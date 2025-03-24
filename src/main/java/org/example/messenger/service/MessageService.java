package org.example.messenger.service;

import org.example.messenger.model.Chat;
import org.example.messenger.model.Message;
import org.example.messenger.model.User;
import org.example.messenger.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    // Отправка сообщения
    public Message sendMessage(Optional<User> senderOptional, Chat chat, String text) {
        // Извлекаем пользователя или выбрасываем исключение, если отсутствует
        User sender = senderOptional.orElseThrow(() -> new IllegalArgumentException("Отправитель не найден"));

        // Проверяем, что пользователь является участником чата
        if (!chat.getParticipants().contains(sender)) {
            throw new IllegalArgumentException("Пользователь не является участником чата");
        }

        Message message = new Message();
        message.setText(text);
        message.setSender(sender);
        message.setChat(chat);
        return messageRepository.save(message);
    }


    // Получение сообщений чата
    public List<Message> getChatMessages(Chat chat) {
        return messageRepository.findByChat(chat);
    }
}