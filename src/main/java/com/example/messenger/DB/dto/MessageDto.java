package com.example.messenger.DB.dto;

public class MessageDto {
    private Long chat_id;
    private String text;

    public MessageDto(Long chat_id, String text) {
        this.chat_id = chat_id;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getChatId() {
        return chat_id;
    }

    public void setChatId(Long chat_id) {
        this.chat_id = chat_id;
    }
}
