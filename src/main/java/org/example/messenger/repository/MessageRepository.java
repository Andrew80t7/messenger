package org.example.messenger.repository;

import org.example.messenger.model.Chat;
import org.example.messenger.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByChat(Chat chat);
}