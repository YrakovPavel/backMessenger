package com.example.messenger.DB.dto;

import java.time.Instant;

public record ChatMessageDto(Long message_id, String username, String text, Instant time){}
