package org.example.messenger.service;

import org.example.messenger.model.Chat;
import org.example.messenger.model.ChatCreateRequest;
import org.example.messenger.model.User;
import org.example.messenger.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ChatService {


    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private UserService userService;
    private UserService userService1;

    // Создание нового чата
    public Chat createChat(Chat chat) {
        // Дополнительная логика создания чата (если требуется)
        return chatRepository.save(chat);
    }

    // Получение чатов пользователя
    public List<Chat> getUserChats(User user) {
        return chatRepository.findByParticipantsContaining(user);
    }

    public Chat findById(Long chatId) {

        return chatRepository.findById(chatId)
                .orElseThrow(() -> new IllegalArgumentException("Чат не найден"));
    }

    public Optional<Chat> findByIdWithParticipants(Long chatId) {
        return chatRepository.findByIdWithParticipants(chatId);
    }

    public Chat createChatWithParticipants(ChatCreateRequest request) {
        Chat chat = new Chat();

        List<User> participants = userService.findAllById(request.getParticipantIds());

        if (participants.isEmpty()) {
            throw new IllegalArgumentException("Не указаны участники чата");
        }

        chat.setParticipants(participants);

        return chatRepository.save(chat);
    }


}