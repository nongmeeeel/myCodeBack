package com.example.mycodeBack.chat.dto.response;

import com.example.mycodeBack.chat.domain.Chat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ChatResponseDTO {
    private Long id;
    private String title;
    private String type;
    private int participantCount;
    private String lastMessage;
    private LocalDateTime lastMessageTime;

    public static ChatResponseDTO toDTO(Chat chat) {
        return ChatResponseDTO.builder()
                .id(chat.getId())
                .title(chat.getTitle())
                .type(chat.getType())
                .build();
    }
}
