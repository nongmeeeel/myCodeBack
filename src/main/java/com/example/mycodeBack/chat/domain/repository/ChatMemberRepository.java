package com.example.mycodeBack.chat.domain.repository;

import com.example.mycodeBack.chat.domain.Chat;
import com.example.mycodeBack.chat.domain.ChatMember;
import com.example.mycodeBack.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatMemberRepository extends JpaRepository<ChatMember, Long> {
    @Query("SELECT cm FROM ChatMember cm WHERE cm.member.id = :memberId AND cm.useYn = 'Y'")
    List<ChatMember> findActiveChatMemberByMemberId(Long memberId);

    @Query("SELECT cm.chat FROM ChatMember cm WHERE cm.member.id = :memberId AND cm.useYn = 'Y'")
    List<Chat> findChatByMemberId(Long memberId);

    boolean existsByChatAndMember(Chat chat, Member member);

    @Query("""
        SELECT DISTINCT cm.chat FROM ChatMember cm
        WHERE cm.chat.type = 'PRIVATE'
        AND cm.chat.id IN (
            SELECT cm2.chat.id FROM ChatMember cm2
            WHERE cm2.member.id IN (:memberId1, :memberId2)
            GROUP BY cm2.chat.id
            HAVING COUNT(cm2) = 2
        )
    """)
    Optional<Chat> findPrivateChatByMemberIds(
            @Param("memberId1") Long memberId1,
            @Param("memberId2") Long memberId2
    );

    List<ChatMember> findByChatId(Long chatId);

}