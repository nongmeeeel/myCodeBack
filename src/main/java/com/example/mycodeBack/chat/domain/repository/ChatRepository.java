package com.example.mycodeBack.chat.domain.repository;

import com.example.mycodeBack.chat.domain.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    @Query("SELECT c FROM Chat c WHERE c.useYn = 'Y'")
    List<Chat> findAllActiveChatRooms();
}
