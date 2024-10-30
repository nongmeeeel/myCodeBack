package com.example.mycodeBack.code.domain;

import com.example.mycodeBack.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class CodeItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String info;
    @ManyToOne
    @JoinColumn(name = "code_category_id")
    private CodeCategory codeCategory;
    private String useYn;

    // 생성자, setter, toString 등 필요한 코드 추가
}