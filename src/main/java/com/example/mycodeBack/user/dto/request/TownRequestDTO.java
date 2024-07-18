package com.example.mycodeBack.user.dto.request;

import com.example.mycodeBack.user.domain.Town;
import com.example.mycodeBack.user.domain.User;
import com.example.mycodeBack.user.dto.response.UserResponseDTO;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TownRequestDTO {
    private final String townCode;
    private final String nm;
    private final String fullNm;
    private final String engNm;
    private final BigDecimal lat;
    private final BigDecimal lng;
    private final Double zoomLevel;

    public static Town toEntity(TownRequestDTO townRequestDTO) {
        return Town.builder()
                .townCode(townRequestDTO.townCode)
                .nm(townRequestDTO.nm)
                .fullNm(townRequestDTO.fullNm)
                .engNm(townRequestDTO.engNm)
                .lat(townRequestDTO.lat)
                .lng(townRequestDTO.lng)
                .zoomLevel(townRequestDTO.zoomLevel)
                .build();
    }
}
