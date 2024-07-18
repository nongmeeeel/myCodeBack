package com.example.mycodeBack.user.domain;

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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String snsCode;
    private String name;
    private String gender;
    private String birthDate;
    private String email;
    private String phoneNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "town_code")
    private Town town;
    private String useYn;
    private LocalDateTime insertDate;
    private LocalDateTime modifyDate;
    private String modifyBy;

    // 생성자, setter, toString 등 필요한 코드 추가
    public void updateTownCode(Town town) {
        this.town = town;
    }
}
