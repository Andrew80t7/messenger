package org.example.messenger.controller;

import org.example.messenger.model.Chat;
import org.example.messenger.model.User;
import org.example.messenger.service.ChatService;
import org.example.messenger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/chats")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserService userService;

    //Получение чатов пользователя
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Chat>> getUserChats(@PathVariable Long userId) {
        return userService.findById(userId)
                .map(user -> ResponseEntity.ok(chatService.getUserChats(user)))
                .orElse(ResponseEntity.notFound().build());

    }


    //Создание чата
    @PostMapping("/create")
    public ResponseEntity<Chat> createChat(@RequestBody Chat chat) {
        Chat createdChat = chatService.createChat(chat);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdChat);
    }
}
