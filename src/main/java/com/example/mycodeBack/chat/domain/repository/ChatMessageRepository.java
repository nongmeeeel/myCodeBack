package com.example.mycodeBack.chat.domain.repository;

import com.example.mycodeBack.chat.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByChatIdOrderBySendAt(Long chatId);

    @Query("SELECT m FROM ChatMessage m " +
            "WHERE m.chat.id = :chatId " +
            "AND m.readYn = 'N' " +
            "AND m.member.id != :memberId")
    List<ChatMessage> findUnreadMessages(Long chatId, Long memberId);
}
