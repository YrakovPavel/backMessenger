package com.example.messenger.DB.repos;

import com.example.messenger.DB.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {

    @Query("SELECT ms FROM Message ms WHERE ms.chat.id=:chat_id")
    List<Message> findAllByChatId(@Param("chat_id") Long chat_id);
}
