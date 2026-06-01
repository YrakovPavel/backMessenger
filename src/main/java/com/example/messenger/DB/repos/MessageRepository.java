package com.example.messenger.DB.repos;

import com.example.messenger.DB.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {
}
