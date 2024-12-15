package com.example.mycodeBack.chat.domain;

import com.example.mycodeBack.common.BaseEntity;
import com.example.mycodeBack.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class ChatMessage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHAT_ID", nullable = false)
    private Chat chat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SENDER_ID", nullable = false)
    private Member member;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String type;

    private LocalDateTime sendAt;

    private String readYn = "N";

    private String deletedYn = "N";
    private String useYn;

    public void setReadYn(String readYn) {
        this.readYn = readYn;
    }
}
