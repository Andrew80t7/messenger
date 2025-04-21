package org.example.messenger.model;

import lombok.Getter;

@Getter
public class ChatMessageDto {
    // Геттеры и Сеттеры
    private Long senderId;
    private Long chatId;
    private String text;

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public void setText(String text) {
        this.text = text;
    }
}
