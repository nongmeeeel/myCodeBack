package com.example.mycodeBack.member.dto.response;

import com.example.mycodeBack.member.domain.Member;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberResponseDTO {

    private Long id;
    private String loginType;
    private String kakaoNickname;
    private String name;
    private String gender;
    private String birthDate;
    private String email;
    private String phoneNumber;
    private TownResponseDTO memberTown;

    public static MemberResponseDTO toDTO(Member member) {
        return MemberResponseDTO.builder()
                .id(member.getId())
                .loginType(member.getLoginType())
                .kakaoNickname(member.getKakaoNickname())
                .name(member.getName())
                .gender(member.getGender())
                .birthDate(member.getBirthDate())
                .email(member.getEmail())
                .phoneNumber(member.getPhoneNumber())
                .memberTown(TownResponseDTO.toDTO(member.getTown()))
                .build();
    }
}
