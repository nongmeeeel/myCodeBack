package com.example.mycodeBack.chat.domain.repository;

import com.example.mycodeBack.chat.domain.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    @Query("SELECT c FROM Chat c WHERE c.useYn = 'Y'")
    List<Chat> findAllActiveChatRooms();

//    @Query("""
//        SELECT DISTINCT c FROM Chat c
//        LEFT JOIN FETCH c.chatMembers cm
//        LEFT JOIN FETCH cm.member m
//        WHERE c.id = :chatId
//    """)
//    Optional<Chat> findByIdWithMembers(@Param("chatId") Long chatId);
}
