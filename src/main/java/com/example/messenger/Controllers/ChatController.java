package com.example.messenger.Controllers;

import com.example.messenger.DB.Chat;
import com.example.messenger.DB.ChatMember;
import com.example.messenger.DB.User;
import com.example.messenger.DB.dto.ChatPreviewDto;
import com.example.messenger.DB.dto.SingleUserLoginDto;
import com.example.messenger.DB.repos.ChatMemberRepository;
import com.example.messenger.DB.repos.ChatRepository;
import com.example.messenger.DB.repos.MessageRepository;
import com.example.messenger.DB.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ChatController {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatMemberRepository chatMemberRepository;
    @Autowired
    private MessageRepository messageRepository;

    @PostMapping("/api/chat/create/dialogue")
    @ResponseStatus(HttpStatus.OK)
    public void createDialogue(@AuthenticationPrincipal UserDetails userDetails,
                               @RequestBody SingleUserLoginDto userLogin){
        String userName = userDetails.getUsername();
        String friendName = userLogin.login();
        if (!userName.equals(friendName)) {
            User currentUser = userRepository.findByLogin(userName).orElseThrow(ResourceNotFoundException::new);

            User friendUser = userRepository.findByLogin(friendName).orElseThrow(ResourceNotFoundException::new);

            //Проверка, нет ли у пользователей уже заведенного диалога
            List<Long> currentChats = chatMemberRepository.findChatIdByUserId(currentUser.getId());
            List<Long> friendChats = chatMemberRepository.findChatIdByUserId(friendUser.getId());

            if (!currentChats.isEmpty() && !friendChats.isEmpty()) {
                currentChats.retainAll(friendChats);
                for (Long chat: currentChats){
                    if (chatRepository.existsChatByIdAndType(chat, "dialogue")){
                        throw new IllegalArgumentException("Users already have dialogue!");
                    }
                }
            }

            Chat chat = chatRepository.save(new Chat("dialogue"));

            ChatMember currentMember = new ChatMember("owner", chat, currentUser);
            ChatMember friendMember = new ChatMember("owner", chat, friendUser);

            chatMemberRepository.save(currentMember);
            chatMemberRepository.save(friendMember);
        }
        else{
            throw new IllegalArgumentException("You can't start chat with yourself!");
        }
    }

    @GetMapping("/chats/preview")
    public ArrayList<ChatPreviewDto> getChatPreviews(@AuthenticationPrincipal UserDetails userDetails){

        String userName = userDetails.getUsername();
        User user = userRepository.findByLogin(userName).orElseThrow(ResourceNotFoundException::new);

        ArrayList<ChatPreviewDto> previewChats = new ArrayList<>();

        List<Long> chats = chatMemberRepository.findChatIdByUserId(user.getId());
        for (long chat: chats){
            long chatFriendId = chatMemberRepository.findChatFriendId(chat, user.getId());
            Optional<User> chatFriend = userRepository.findById(chatFriendId);
            if (chatFriend.isPresent()){
                User chatFriendEntity = chatFriend.get();
                Optional<String> message = messageRepository.findPreviewMessage(chat);
                String messageToReturn = message.orElse("");

                previewChats.add(new ChatPreviewDto(
                        chat, chatFriendEntity.getLogin(),
                        "http://localhost:8080/uploads/userAvatars/" + chatFriendEntity.getAvatarUrl(),
                        messageToReturn));
            }
        }
        return previewChats;
    }
}
