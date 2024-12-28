package com.example.mycodeBack.chat.domain.repository;

import com.example.mycodeBack.chat.domain.Chat;
import com.example.mycodeBack.chat.dto.response.ChatWithMemberResponseDTO;
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

    @Query("""
                SELECT NEW com.example.mycodeBack.chat.dto.response.ChatWithMemberResponseDTO(
                    c.id,                    
                    c.title,                 
                    c.type,                   
                    c.lastMessage,         
                    m.id,                    
                    m.name,                   
                    m.gender,                
                    m.birthDate,
                    cMsg.member.id,
                    cMsg.content,
                    cMsg.readYn
                )
                FROM Chat c
                JOIN c.chatMembers cm1 ON cm1.member.id = :memberId
                JOIN c.chatMembers cm2 ON cm2.member.id != :memberId
                JOIN cm2.member m
                LEFT JOIN ChatMessage cMsg ON cMsg.chat = c
                AND (
                    cMsg.sendAt IS NULL OR
                    cMsg.sendAt = (
                        SELECT MAX(cm.sendAt)
                        FROM ChatMessage cm
                        WHERE cm.chat = c
                    )
                )
                ORDER BY c.lastMessage DESC NULLS LAST
            """)
    List<ChatWithMemberResponseDTO> findChatListByMemberId(@Param("memberId") Long memberId);

    @Query("""
                SELECT NEW com.example.mycodeBack.chat.dto.response.ChatWithMemberResponseDTO(
                    c.id,                    
                    c.title,                 
                    c.type,                   
                    c.lastMessage,         
                    m.id,                    
                    m.name,                   
                    m.gender,                
                    m.birthDate,
                    cMsg.member.id,
                    cMsg.content,
                    cMsg.readYn
                )
                FROM Chat c
                JOIN c.chatMembers cm ON cm.member.id != :thisId
                JOIN cm.member m
                LEFT JOIN ChatMessage cMsg ON cMsg.chat = c
                WHERE c.id = :chatId
                AND (
                    cMsg.sendAt IS NULL OR
                    cMsg.sendAt = (
                        SELECT MAX(cm.sendAt)
                        FROM ChatMessage cm
                        WHERE cm.chat = c
                    )
                )
                ORDER BY c.lastMessage DESC NULLS LAST
            """)
    ChatWithMemberResponseDTO findChatWithMember(@Param("chatId") Long chatId, @Param("thisId") Long thisId);
}
