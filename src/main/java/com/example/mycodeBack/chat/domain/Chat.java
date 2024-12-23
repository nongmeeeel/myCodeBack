package com.example.mycodeBack.chat.domain;

import com.example.mycodeBack.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Chat extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String type;
    private LocalDateTime lastMessage;
    private String useYn;

    @Builder.Default
    @OneToMany(mappedBy = "chat", fetch = FetchType.LAZY)
    private List<ChatMember> chatMembers = new ArrayList<>();

    public void setLastMessage(LocalDateTime localDateTime) {
        this.lastMessage = localDateTime;
    }

    public List<ChatMember> getChatMembers() {
        return chatMembers;
    }

    public void addChatMember(ChatMember chatMember) {
        chatMembers.add(chatMember);
        chatMember.addChat(this); // 양방향 관계 설정
    }
}
