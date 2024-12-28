package com.example.mycodeBack.member.domain;

import com.example.mycodeBack.common.BaseEntity;
import com.example.mycodeBack.member.domain.type.MemberRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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
    @JoinColumn(name = "town_id")
    private Town town;

    @Enumerated(value = STRING)
    private MemberRole role;

    private String refreshToken;

    private String useYn;




    @OneToMany(mappedBy = "member") // "member"는 MemberCodeMap에서 연관된 필드 이름
    private List<MemberCodeMap> memberCodeMap;

    @OneToMany(mappedBy = "member") // "member"는 MemberCodeMap에서 연관된 필드 이름
    private List<MemberCodeFilterMap> memberCodeFilterMap;

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

    public void signUp(String name, String gender, String birthDate) {
        this.name = name;
        this.gender = gender;
        this.birthDate = birthDate;
        this.role = MemberRole.MEMBER;
    }

    public void updateMember(String name, String gender, String birthDate) {
        this.name = name;
        this.gender = gender;
        this.birthDate = birthDate;
    }
}
