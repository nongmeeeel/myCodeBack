package com.example.mycodeBack.user.domain;

import com.example.mycodeBack.code.domain.CodeItem;
import com.example.mycodeBack.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCodeMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "code_item_id")
    private CodeItem codeItem;
    private String useYn;
    private LocalDateTime insertDate;
    private LocalDateTime modifyDate;
    private String modifyBy;

    // 생성자, setter, toString 등 필요한 코드 추가
}