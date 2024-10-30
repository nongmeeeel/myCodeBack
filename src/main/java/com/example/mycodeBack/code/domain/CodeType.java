package com.example.mycodeBack.code.domain;

import com.example.mycodeBack.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
public class CodeType extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String info;
    private String useYn;
    // mappedBy
    @OneToMany(mappedBy = "codeType", fetch = FetchType.LAZY)
    private List<CodeCategory> codeCategories;

    // 생성자, setter, toString 등 필요한 코드 추가
}
