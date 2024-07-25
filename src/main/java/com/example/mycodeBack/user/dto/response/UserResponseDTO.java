package com.example.mycodeBack.user.dto.response;

import com.example.mycodeBack.user.domain.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDTO {

    private final Long id;
    private final String kakaoId;
    private final String kakaoNickname;
    private final String name;
    private final String gender;
    private final String birthDate;
    private final String email;
    private final String phoneNumber;
    private final TownResponseDTO userTown;
    private final String role;

    public static UserResponseDTO toDTO(final User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .kakaoId(user.getKakaoId())
                .kakaoNickname(user.getKakaoNickname())
                .name(user.getName())
                .gender(user.getGender())
                .birthDate(user.getBirthDate())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .userTown(TownResponseDTO.toDTO(user.getTown()))
                .role(user.getRole())
                .build();
    }
}
