package org.example.messenger.repository;

import org.example.messenger.model.Chat;
import org.example.messenger.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    List<Chat> findByParticipantsContaining(User user);

    @Query("SELECT c FROM Chat c LEFT JOIN FETCH c.participants WHERE c.id = :chatId")
    Optional<Chat> findByIdWithParticipants(@Param("chatId") Long chatId);
}