package com.example.mycodeBack.user.dto.response;

import com.example.mycodeBack.user.domain.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDTO {

    private final Long id;
    private final String snsCode;
    private final String name;
    private final String gender;
    private final String birthDate;
    private final String email;
    private final String phoneNumber;
    private final TownResponseDTO userTown;

    public static UserResponseDTO toDTO(final User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .snsCode(user.getSnsCode())
                .name(user.getName())
                .gender(user.getGender())
                .birthDate(user.getBirthDate())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .userTown(TownResponseDTO.toDTO(user.getTown()))
                .build();
    }
}
