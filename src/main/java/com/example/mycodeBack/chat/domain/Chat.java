package com.example.mycodeBack.chat.domain;

import com.example.mycodeBack.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
public class Chat extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String type;
    private LocalDateTime lastMessage;
    private String useYn;

    public void setLastMessage (LocalDateTime localDateTime) {
        this.lastMessage = localDateTime;
    }
}
