package com.example.messenger.DB.repos;

import com.example.messenger.DB.ChatMember;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatMemberRepository extends CrudRepository<ChatMember, Long> {

    @Query("SELECT cm.chat.id FROM ChatMember cm WHERE cm.user.id = :userId")
    List<Long> findChatIdByUserId(@Param("userId") long userId);

    @Query("SELECT cm.user.id FROM ChatMember cm WHERE cm.chat.id = :chatId AND cm.user.id != :userId")
    Long findChatFriendId(@Param("chatId") long chatId, @Param("userId") long userId);

    boolean existsByChat_IdAndUser_Id(long chatId, long userId);
}
