package org.example.messenger.repository;

import org.example.messenger.model.Chat;
import org.example.messenger.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findByParticipantsContaining(User user);
}