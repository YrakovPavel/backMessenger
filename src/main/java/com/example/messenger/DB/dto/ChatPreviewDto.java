package com.example.messenger.DB.dto;

import java.time.Instant;

public record ChatPreviewDto(Long chat_id, String name, String avatarUrl, String text, Instant time){};
