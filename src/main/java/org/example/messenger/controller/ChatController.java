package org.example.messenger.controller;

import org.example.messenger.model.Chat;
import org.example.messenger.model.ChatCreateRequest;
import org.example.messenger.model.User;
import org.example.messenger.repository.ChatRepository;
import org.example.messenger.service.ChatService;
import org.example.messenger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chats")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserService userService;
    @Autowired
    private ChatRepository chatRepository;

    //Получение чатов пользователя
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Chat>> getUserChats(@PathVariable Long userId) {
        return userService.findById(userId)
                .map(user -> ResponseEntity.ok(chatService.getUserChats(user)))
                .orElse(ResponseEntity.notFound().build());

    }

    //Создание чата
    @PostMapping("/create")
    public ResponseEntity<Chat> createChat(@RequestBody ChatCreateRequest request) {
        Chat createdChat = chatService.createChatWithParticipants(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdChat);
    }

    @PostMapping("/chats/{chatId}/add-user/{user-Id}")
    public ResponseEntity<?> addUserToChat(
            @PathVariable Long chatId,
            @PathVariable Long userId, @PathVariable("user-Id") String parameter)
    {
        Chat chat = chatService.findByIdWithParticipants(chatId).
                orElseThrow(() -> new IllegalArgumentException("Chat not found"));
        User user = userService.findById(userId).
                orElseThrow(() -> new IllegalArgumentException("User not found"));

        chat.getParticipants().add(user);
        chatRepository.save(chat);
        return ResponseEntity.ok().build();
    }

}
