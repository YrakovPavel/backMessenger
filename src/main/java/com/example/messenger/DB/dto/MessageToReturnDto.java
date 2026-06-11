package com.example.messenger.DB.dto;

import java.time.Instant;

public record MessageToReturnDto(Long chat_id, Long message_id,
                                 String username, String text, Instant time){}
