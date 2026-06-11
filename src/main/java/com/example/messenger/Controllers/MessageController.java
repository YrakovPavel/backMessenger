package com.example.messenger.Controllers;

import com.example.messenger.DB.Chat;
import com.example.messenger.DB.Message;
import com.example.messenger.DB.User;
import com.example.messenger.DB.dto.MessageToReturnDto;
import com.example.messenger.DB.dto.MessageDto;
import com.example.messenger.DB.repos.ChatRepository;
import com.example.messenger.DB.repos.MessageRepository;
import com.example.messenger.DB.repos.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MessageController {

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping("/api/message/send")
    public void sendMessage(@AuthenticationPrincipal UserDetails userDetails, @RequestBody MessageDto message){
        User user = userRepository.findByLogin(userDetails.getUsername())
                .orElseThrow(ResourceNotFoundException::new);
        Chat chat = chatRepository.findById(message.getChatId())
                .orElseThrow(ResourceNotFoundException::new);

        Message newMessage = messageRepository.save(new Message(chat, user, message.getText()));
        messagingTemplate.convertAndSend("/topic/chat/" + chat.getId(),
                new MessageToReturnDto(
                        message.getChatId(),
                        newMessage.getId(),
                        newMessage.getSender().getLogin(),
                        newMessage.getText(),
                        newMessage.getCreatedAt()));
    }

    @GetMapping("/chats/{chat_id}")
    public ArrayList<MessageToReturnDto> getMessages(@AuthenticationPrincipal UserDetails userDetails,
                                                     @PathVariable("chat_id") Long chatId){
        ArrayList<MessageToReturnDto> messagesToReturn = new ArrayList<>();
        List<Message> messages = messageRepository.findAllByChatId(chatId);
        if (!messages.isEmpty()){
            for (Message message : messages){
                messagesToReturn.add(new MessageToReturnDto(
                        chatId,
                        message.getId(),
                        message.getSender().getLogin(),
                        message.getText(),
                        message.getCreatedAt()));
            }
        }
        return messagesToReturn;
    }
}
