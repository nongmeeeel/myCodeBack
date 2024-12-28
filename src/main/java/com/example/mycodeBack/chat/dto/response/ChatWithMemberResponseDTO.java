package com.example.mycodeBack.chat.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data

public class ChatWithMemberResponseDTO {
    private Long id;
    private String title;
    private String type;
    private LocalDateTime lastMessage;
    private Long memberId;
    private String memberName;
    private String memberGender;
    private String memberBirthDate;
    private Long lastSenderId;
    private String lastContent;
    private String lastReadYn;
}
