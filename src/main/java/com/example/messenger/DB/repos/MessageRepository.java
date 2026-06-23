package com.example.messenger.DB.repos;

import com.example.messenger.DB.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends CrudRepository<Message, Long> {

    @Query("SELECT ms FROM Message ms WHERE ms.chat.id=:chat_id")
    List<Message> findAllByChatId(@Param("chat_id") Long chat_id);

    @Query("SELECT ms FROM Message ms WHERE ms.chat.id=:chat_id ORDER BY ms.id DESC LIMIT 1")
    Optional<Message> findPreviewMessage(@Param("chat_id") Long chat_id);
}
