package com.example.mycodeBack.member.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpRequestDTO {
    private final String name;
    private final String gender;
    private final String birthDate;
    private final TownRequestDTO memberTown;
}
