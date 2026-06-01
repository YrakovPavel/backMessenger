package com.example.messenger.Controllers;

import com.example.messenger.DB.Chat;
import com.example.messenger.DB.ChatMember;
import com.example.messenger.DB.User;
import com.example.messenger.DB.dto.SingleUserLoginDto;
import com.example.messenger.DB.repos.ChatMemberRepository;
import com.example.messenger.DB.repos.ChatRepository;
import com.example.messenger.DB.repos.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ChatController {

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatMemberRepository chatMemberRepository;

    @PostMapping("/api/chat/create/dialogue")
    @ResponseStatus(HttpStatus.OK)
    public void createDialogue(@AuthenticationPrincipal UserDetails userDetails,
                               @RequestBody SingleUserLoginDto userLogin){
        String userName = userDetails.getUsername();
        String friendName = userLogin.login();
        if (!userName.equals(friendName)) {
            Optional<User> currentUser = Optional.of(userRepository.findByLogin(userName)
                    .orElseThrow(ResourceNotFoundException::new));

            Optional<User> friendUser = Optional.of(userRepository.findByLogin(friendName)
                    .orElseThrow(ResourceNotFoundException::new));

            //Проверка, нет ли у пользователей уже заведенного диалога
            List<Long> currentChats = chatMemberRepository.findChatIdByUserId(currentUser.get().getId());
            List<Long> friendChats = chatMemberRepository.findChatIdByUserId(friendUser.get().getId());

            if (!currentChats.isEmpty() && !friendChats.isEmpty()) {
                currentChats.retainAll(friendChats);
                for (Long chat: currentChats){
                    if (chatRepository.existsChatByIdAndType(chat, "dialogue")){
                        throw new IllegalArgumentException("Users already have dialogue!");
                    }
                }
            }

            Chat chat = chatRepository.save(new Chat("dialogue"));

            ChatMember currentMember = new ChatMember("owner", chat, currentUser.get());
            ChatMember friendMember = new ChatMember("owner", chat, friendUser.get());

            chatMemberRepository.save(currentMember);
            chatMemberRepository.save(friendMember);
        }
        else{
            throw new IllegalArgumentException("You can't start chat with yourself!");
        }
    }
}
