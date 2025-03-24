package org.example.messenger.controller;

import org.example.messenger.model.Chat;
import org.example.messenger.model.Message;
import org.example.messenger.model.User;
import org.example.messenger.service.ChatService;
import org.example.messenger.service.MessageService;
import org.example.messenger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/send")
    public Message sendMessage(@RequestParam Long senderId, @RequestParam Long chatId, @RequestParam String text) {
        Optional<User> sender = userService.findById(senderId);
        Chat chat = chatService.findById(chatId);
        return messageService.sendMessage(sender, chat, text);
    }

    @GetMapping("/chat/{chatId}")
    public List<Message> getChatMessages(@PathVariable Long chatId) {
        Chat chat = chatService.findById(chatId);
        return messageService.getChatMessages(chat);
    }
}