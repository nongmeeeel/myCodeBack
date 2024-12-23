package com.example.mycodeBack.chat.dto.request;

import com.example.mycodeBack.chat.domain.ChatMessage;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ChatMessageRequestDTO {
    private Long id;
    private Long chatId;       // 채팅방 ID 추가
    private Integer senderId;
    private String content;
    private String type;
    private String readStatus;
    private LocalDateTime sendAt;
}