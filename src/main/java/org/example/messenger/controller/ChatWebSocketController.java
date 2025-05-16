package org.example.messenger.controller;

import org.example.messenger.model.Chat;
import org.example.messenger.model.ChatMessageDto;
import org.example.messenger.model.Message;
import org.example.messenger.model.User;
import org.example.messenger.service.ChatService;
import org.example.messenger.service.MessageService;
import org.example.messenger.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Controller
public class ChatWebSocketController {
    private static final Logger log = LoggerFactory.getLogger(ChatWebSocketController.class);

    private final UserService userService;
    private final ChatService chatService;
    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;

    public ChatWebSocketController(UserService userService,
                                   ChatService chatService,
                                   MessageService messageService,
                                   SimpMessagingTemplate messagingTemplate) {
        this.userService       = userService;
        this.chatService       = chatService;
        this.messageService    = messageService;
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * Ожидает на /app/send JSON вида:
     * { "senderId":123, "chatId":1, "text":"Привет!", "timestamp":"2025-05-…" }
     */
    @MessageMapping("/send")
public void handleSend(@Payload ChatMessageDto dto) {
    log.info("WS: получил DTO {}", dto);

    // 1) Найти и распаковать пользователя
    Optional<User> senderOpt = userService.findById(dto.getSenderId());
    User sender = senderOpt.orElseThrow(
        () -> new IllegalArgumentException("Пользователь не найден: " + dto.getSenderId())
    );

    Optional<Chat> chatOpt = chatService.findByIdWithParticipants(dto.getChatId());
    Chat chat = chatOpt.orElseThrow(
        () -> new IllegalArgumentException("Чат не найден: " + dto.getChatId())
    );

    List<User> participants = chat.getParticipants();
    Long chatId = chat.getId();
    Long senderId = sender.getId();

    boolean isMember = participants.stream()
        .anyMatch(u -> u.getId().equals(senderId));
    if (!isMember) {
        throw new IllegalArgumentException(
            "Пользователь " + senderId + " не является участником чата " + chatId
        );
    }

    Message saved = messageService.sendMessage(Optional.of(sender), Optional.of(chat), dto.getText());
    ChatMessageDto out = new ChatMessageDto();
    out.setSenderId(saved.getSender().getId());
    out.setChatId(saved.getChat().getId());
    out.setText(saved.getText());
    out.setTimestamp(Instant.now().toString());

    String destination = "/topic/chat/" + chatId;
    messagingTemplate.convertAndSend(destination, out);

    log.info("WS: отправил сообщение (id={}) в {}", saved.getId(), destination);
}



}
