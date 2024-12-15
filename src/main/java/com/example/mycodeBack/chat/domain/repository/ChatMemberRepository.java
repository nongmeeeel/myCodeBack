package com.example.mycodeBack.chat.domain.repository;

import com.example.mycodeBack.chat.domain.Chat;
import com.example.mycodeBack.chat.domain.ChatMember;
import com.example.mycodeBack.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMemberRepository extends JpaRepository<ChatMember, Long> {
    @Query("SELECT cm FROM ChatMember cm WHERE cm.member.id = :memberId AND cm.useYn = 'Y'")
    List<ChatMember> findActiveChatMemberByMemberId(Long memberId);

    @Query("SELECT cm.chat FROM ChatMember cm WHERE cm.member.id = :memberId AND cm.useYn = 'Y'")
    List<Chat> findChatByMemberId(Long memberId);

    boolean existsByChatAndMember(Chat chat, Member member);
}