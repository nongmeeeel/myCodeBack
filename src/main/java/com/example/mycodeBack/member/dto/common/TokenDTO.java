package com.example.mycodeBack.member.dto.common;

import lombok.Data;

@Data
public class TokenDTO {
    final String accessToken;
    final String refreshToken;
}
