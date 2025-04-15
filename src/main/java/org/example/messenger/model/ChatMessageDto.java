package org.example.messenger.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChatMessageDto {
    private Long senderId;
    private Long chatId;
    private String text;

    public ChatMessageDto() {}

    public ChatMessageDto(Long senderId, Long chatId, String text) {
        this.senderId = senderId;
        this.chatId = chatId;
        this.text = text;
    }

}
