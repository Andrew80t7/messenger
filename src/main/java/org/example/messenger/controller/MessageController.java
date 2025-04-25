package org.example.messenger.controller;

import org.example.messenger.model.Chat;
import org.example.messenger.model.ChatMessageDto;
import org.example.messenger.model.Message;
import org.example.messenger.model.User;
import org.example.messenger.service.ChatService;
import org.example.messenger.service.MessageService;
import org.example.messenger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private UserService userService;

    @Autowired
    private ChatService chatService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;


        @MessageMapping("/send")
        public void sendMessage(@Payload ChatMessageDto chatMessageDto) {
            Long senderId = chatMessageDto.getSenderId();
            Long chatId = chatMessageDto.getChatId();
            String text = chatMessageDto.getText();

            Optional<User> senderOpt = userService.findById(senderId);

            if (senderOpt.isPresent()) {
                User sender = senderOpt.get();
                Chat chat = chatService.findById(chatId);
                Message message = messageService.sendMessage(Optional.of(sender), chat, text);
                // Отправляем сообщение всем подписчикам соответствующего топика
                messagingTemplate.convertAndSend("/topic/chat/" + chatId, message);
            }
        }

    // Получение сообщений чата (REST эндпоинт)
    @GetMapping("/chat/{chatId}")
    public List<Message> getChatMessages(@PathVariable Long chatId) {
        Chat chat = chatService.findById(chatId);
        return messageService.getChatMessages(chat);
    }
}
