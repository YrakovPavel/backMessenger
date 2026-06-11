package com.example.messenger.WebSocketControllers;

import com.example.messenger.DB.User;
import com.example.messenger.DB.dto.MessageToReturnDto;
import com.example.messenger.DB.repos.ChatMemberRepository;
import com.example.messenger.DB.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

@Controller
public class MessageWebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatMemberRepository chatMemberRepository;

    @MessageMapping("/getMessage")
    public void greeting(@AuthenticationPrincipal UserDetails userDetails, MessageToReturnDto message){
        User user = userRepository.findByLogin(userDetails.getUsername())
                .orElseThrow(ResourceNotFoundException::new);
        boolean userInChat = chatMemberRepository.existsByChat_IdAndUser_Id(message.chat_id(), user.getId());
        if (userInChat){
            messagingTemplate.convertAndSend("/topic/chat/" + message.chat_id(), message);
        }
    }
}
