package com.example.messenger.DB.dto;

public class MessageDto {
    private Long id;
    private String text;
    private Long chat_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
