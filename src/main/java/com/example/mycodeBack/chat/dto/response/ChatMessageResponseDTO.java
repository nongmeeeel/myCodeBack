package com.example.mycodeBack.chat.dto.response;

import com.example.mycodeBack.chat.domain.ChatMessage;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ChatMessageResponseDTO {
    private Long id;
    private Long chatId;
    private Long senderId;
    private String content;
    private String type;
    private LocalDateTime sendAt;
    private String readStatus;

    public static ChatMessageResponseDTO toDTO(ChatMessage chatMessage) {
        return ChatMessageResponseDTO.builder()
                .id(chatMessage.getId())
                .chatId(chatMessage.getChat().getId())
                .senderId(chatMessage.getMember().getId())
                .content(chatMessage.getContent())
                .type(chatMessage.getType())
                .sendAt(chatMessage.getSendAt())
                .readStatus(chatMessage.getReadYn())
                .build();
    }
}