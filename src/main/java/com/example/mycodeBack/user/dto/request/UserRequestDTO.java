package com.example.mycodeBack.user.dto.request;

import com.example.mycodeBack.user.domain.User;
import com.example.mycodeBack.user.dto.response.UserResponseDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequestDTO {
    private final Long id;
    private final String snsCode;
    private final String name;
    private final String gender;
    private final String birthDate;
    private final String email;
    private final String phoneNumber;
    private final String townCode;
}
