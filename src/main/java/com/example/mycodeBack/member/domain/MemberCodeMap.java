package com.example.mycodeBack.member.domain;

import com.example.mycodeBack.code.domain.CodeItem;
import com.example.mycodeBack.common.BaseEntity;
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
public class MemberCodeMap extends BaseEntity {
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

    // 생성자, setter, toString 등 필요한 코드 추가
}