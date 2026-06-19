package com.example.messenger.DB.repos;

import com.example.messenger.DB.Chat;
import org.springframework.data.repository.CrudRepository;

public interface ChatRepository extends CrudRepository<Chat, Long> {
    boolean existsChatByIdAndType(Long id, String type);
}
