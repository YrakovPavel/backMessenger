package com.example.messenger.websocketcontrollers;

import com.example.messenger.DB.dto.ChatPreviewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

@Controller
public class ChatWebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/getChat")
    public void getChat(@AuthenticationPrincipal UserDetails userDetails, ChatPreviewDto chatPreviewDto){
        messagingTemplate.convertAndSendToUser(userDetails.getUsername(), "/queue/chats", chatPreviewDto);
    }
}
