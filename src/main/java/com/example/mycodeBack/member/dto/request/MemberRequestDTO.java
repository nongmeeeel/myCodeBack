package com.example.mycodeBack.member.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberRequestDTO {
    private final Long id;
    private final String snsCode;
    private final String name;
    private final String gender;
    private final String birthDate;
    private final String email;
    private final String phoneNumber;
    private final String townCode;
}
