package com.example.mycodeBack.code.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class CodeItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String info;
    @ManyToOne
    @JoinColumn(name = "code_category_id")
    private CodeCategory codeCategory;
    private String useYn;
    private LocalDateTime insertDate;
    private LocalDateTime modifyDate;
    private String modifyBy;

    // 생성자, setter, toString 등 필요한 코드 추가
}