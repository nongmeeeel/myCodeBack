package com.example.mycodeBack.code.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
public class CodeType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String info;
    private String useYn;
    private LocalDateTime insertDate;
    private LocalDateTime modifyDate;
    private String modifyBy;

    // mappedBy
    @OneToMany(mappedBy = "codeType", fetch = FetchType.LAZY)
    private List<CodeCategory> codeCategories;

    // 생성자, setter, toString 등 필요한 코드 추가
}
