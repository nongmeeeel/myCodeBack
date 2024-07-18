package com.example.mycodeBack.code.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
public class CodeCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String info;
    @ManyToOne
    @JoinColumn(name = "code_type_id")
    private CodeType codeType;
    private String useYn;
    private LocalDateTime insertDate;
    private LocalDateTime modifyDate;
    private String modifyBy;

    // mappedBy
    @OneToMany(mappedBy = "codeCategory", fetch = FetchType.LAZY)
    private List<CodeItem> codeItems;

    // 생성자, setter, toString 등 필요한 코드 추가
}