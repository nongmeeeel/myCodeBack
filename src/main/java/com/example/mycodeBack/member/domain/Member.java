package com.example.mycodeBack.member.domain;

import com.example.mycodeBack.common.BaseEntity;
import com.example.mycodeBack.member.domain.type.MemberRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String pw;
    private String loginType;
    private String kakaoNickname;
    private String name;
    private String gender;
    private String birthDate;
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "town_code")
    private Town town;

    @Enumerated(value = STRING)
    private MemberRole role;

    private String refreshToken;

    private String useYn;

    // 권한 수정
//    public void addRole(MemberRole memberRole) {
//        roleList.add(memberRole);
//    }
//    public void clearRole() {
//        roleList.clear();
//    }

    public void changeKakaoNickname(String nickname) {
        this.kakaoNickname = nickname;
    }

    public void changePw(String pw) {
        this.pw = pw;
    }

    // 생성자, setter, toString 등 필요한 코드 추가
    public void updateTownCode(Town town) {
        this.town = town;
    }

    public void updateRefreshToken(String refreshToken) {this.refreshToken = refreshToken; }
}
