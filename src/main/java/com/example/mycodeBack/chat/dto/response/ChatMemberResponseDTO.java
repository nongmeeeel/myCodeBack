package com.example.mycodeBack.chat.dto.response;

import com.example.mycodeBack.chat.domain.ChatMember;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Builder
public class ChatMemberResponseDTO {
    private Long id;
    private Long chatId;
    private Long memberId;
    private String role;
    private LocalDateTime joinedAt;

    public static ChatMemberResponseDTO toDTO(ChatMember chatMember) {
        return ChatMemberResponseDTO.builder()
                .id(chatMember.getId())
                .chatId(chatMember.getChat().getId())
                .memberId(chatMember.getMember().getId())
                .role(chatMember.getRole())
                .joinedAt(chatMember.getJoinedAt())
                .build();
    }
}
