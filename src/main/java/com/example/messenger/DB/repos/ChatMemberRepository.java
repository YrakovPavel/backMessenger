package com.example.messenger.DB.repos;

import com.example.messenger.DB.ChatMember;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatMemberRepository extends CrudRepository<ChatMember, Long> {
    @Query("SELECT cm.chat.id FROM ChatMember cm WHERE cm.user.id = :userId")
    List<Long> findChatIdByUserId(@Param("userId") long userId);
}
