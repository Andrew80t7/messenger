package org.example.messenger.service;

import jakarta.transaction.Transactional;
import org.example.messenger.model.Chat;
import org.example.messenger.model.Message;
import org.example.messenger.model.User;
import org.example.messenger.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.messenger.repository.ChatRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ChatRepository chatRepository;


    @Transactional
    public Message sendMessage(Optional<User> senderOptional, Optional<Chat> chat, String text) {
        User sender = senderOptional.orElseThrow(() ->
                new IllegalArgumentException("Отправитель не найден"));

        Chat persistentChat = chatRepository.findByIdWithParticipants(chat.get().getId()).
                orElseThrow(() -> new IllegalArgumentException("Чат не найден"));

        if (!persistentChat.getParticipants().contains(sender)) {
            throw new IllegalArgumentException("Пользователь не является участником чата");
        }

        Message message = new Message();
        message.setText(text);
        message.setSender(sender);
        message.setChat(persistentChat);
        return messageRepository.save(message);
    }

    public List<Message> getChatMessages(Chat chat) {
        return messageRepository.findByChat(chat);
    }
}