package com.example.mycodeBack.member.domain;

import com.example.mycodeBack.code.domain.CodeItem;
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
public class MemberCodeMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne
    @JoinColumn(name = "code_item_id")
    private CodeItem codeItem;
    private String useYn;
    private LocalDateTime insertDate;
    private LocalDateTime modifyDate;
    private String modifyBy;

    // 생성자, setter, toString 등 필요한 코드 추가
}