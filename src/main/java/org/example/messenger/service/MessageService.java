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
import java.util.stream.Collectors;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ChatRepository chatRepository;


    @Transactional
    public Message sendMessage(Optional<User> senderOptional, Optional<Chat> chat, String text) {
        // Извлекаем пользователя или выбрасываем исключение, если отсутствует
        User sender = senderOptional.orElseThrow(() ->
                new IllegalArgumentException("Отправитель не найден"));

        // Повторно загружаем Chat с участниками
        Chat persistentChat = chatRepository.findByIdWithParticipants(chat.get().getId()).
                orElseThrow(() -> new IllegalArgumentException("Чат не найден"));


        // Теперь можно обращаться к persistentChat.getParticipants() без LazyInitializationException.
        if (!persistentChat.getParticipants().contains(sender)) {
            throw new IllegalArgumentException("Пользователь не является участником чата");
        }

        Message message = new Message();
        message.setText(text);
        message.setSender(sender);
        message.setChat(persistentChat);
        return messageRepository.save(message);
    }


    // Получение сообщений чата
    public List<Message> getChatMessages(Chat chat) {
        return messageRepository.findByChat(chat);
    }
}