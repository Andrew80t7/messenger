package org.example.messenger.controller;

import org.example.messenger.model.ChatMessageDto;
import org.example.messenger.model.Chat;
import org.example.messenger.model.Message;
import org.example.messenger.model.User;
import org.example.messenger.service.ChatService;
import org.example.messenger.service.MessageService;
import org.example.messenger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class MessageWebSocketController {

    @Autowired
    private UserService userService;

    @Autowired
    private ChatService chatService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // Обработка входящих сообщений по адресу /app/chat.sendMessage
    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatMessageDto chatMessageDto) {
        // Получаем отправителя
        User sender = userService.findById(chatMessageDto.getSenderId())
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));
        // Получаем чат
        Chat chat = chatService.findById(chatMessageDto.getChatId());
        // Сохраняем сообщение (метод sendMessage можно сделать так, чтобы он принимал User, Chat, текст)
        Message message = messageService.sendMessage(Optional.ofNullable(sender), chat, chatMessageDto.getText());
        // Рассылаем сообщение всем подписчикам, подписанным на канал /topic/chat/{chatId}
        messagingTemplate.convertAndSend("/topic/chat/" + chat.getId(), message);
    }
}
