package com.example.messenger.Controllers;

import com.example.messenger.DB.Chat;
import com.example.messenger.DB.Message;
import com.example.messenger.DB.User;
import com.example.messenger.DB.dto.MessageDto;
import com.example.messenger.DB.repos.ChatRepository;
import com.example.messenger.DB.repos.MessageRepository;
import com.example.messenger.DB.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public void sendMessage(@AuthenticationPrincipal UserDetails userDetails, @RequestBody MessageDto messageDto){
        Optional<User> user = Optional.of(userRepository.findByLogin(userDetails.getUsername())
                .orElseThrow(ResourceNotFoundException::new));
        Optional<Chat> chat = Optional.of(chatRepository.findById(messageDto.getChatId())
                .orElseThrow(ResourceNotFoundException::new));

        Message message = new Message(chat.get(), user.get() ,messageDto.getText());
        messageRepository.save(message);
    }
}
