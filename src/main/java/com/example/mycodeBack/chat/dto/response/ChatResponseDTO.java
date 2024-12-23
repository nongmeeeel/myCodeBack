package com.example.mycodeBack.chat.dto.response;

import com.example.mycodeBack.chat.domain.Chat;
import com.example.mycodeBack.chat.domain.ChatMember;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class ChatResponseDTO {
    private Long id;
    private String title;
    private String type;
    private LocalDateTime lastMessage;
    private List<ChatMemberResponseDTO> chatMembers;  // 추가

    public static ChatResponseDTO toDTO(Chat chat) {
        return ChatResponseDTO.builder()
                .id(chat.getId())
                .title(chat.getTitle())
                .type(chat.getType())
                .lastMessage(chat.getLastMessage())
                .chatMembers(chat.getChatMembers().stream()
                        .map(ChatMemberResponseDTO::toDTO)
                        .collect(Collectors.toList()))
                .build();
    }
}

