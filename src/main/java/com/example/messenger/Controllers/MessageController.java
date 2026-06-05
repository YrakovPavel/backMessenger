package com.example.messenger.Controllers;

import com.example.messenger.DB.Chat;
import com.example.messenger.DB.Message;
import com.example.messenger.DB.User;
import com.example.messenger.DB.dto.ChatMessageDto;
import com.example.messenger.DB.dto.MessageDto;
import com.example.messenger.DB.repos.ChatRepository;
import com.example.messenger.DB.repos.MessageRepository;
import com.example.messenger.DB.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
public class MessageController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ChatRepository chatRepository;

    @PostMapping("/api/message/send")
    public void sendMessage(@AuthenticationPrincipal UserDetails userDetails, @RequestBody MessageDto message){
        Optional<User> user = Optional.of(userRepository.findByLogin(userDetails.getUsername())
                .orElseThrow(ResourceNotFoundException::new));
        Optional<Chat> chat = Optional.of(chatRepository.findById(message.getChatId())
                .orElseThrow(ResourceNotFoundException::new));

        Message newMessage = new Message(chat.get(), user.get(), message.getText());
        messageRepository.save(newMessage);
    }

    @GetMapping("/chats/{chat_id}")
    public ArrayList<ChatMessageDto> getMessages(@AuthenticationPrincipal UserDetails userDetails,
                                                 @PathVariable("chat_id") Long chatId){
        ArrayList<ChatMessageDto> messagesToReturn = new ArrayList<>();
        List<Message> messages = messageRepository.findAllByChatId(chatId);
        if (!messages.isEmpty()){
            for (Message message : messages){
                messagesToReturn.add(new ChatMessageDto(
                        message.getId(),
                        message.getSender().getLogin(),
                        message.getText(),
                        message.getCreatedAt()));
            }
        }
        return messagesToReturn;
    }
}
