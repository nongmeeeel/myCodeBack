package com.example.mycodeBack.member.dto.response;

import com.example.mycodeBack.member.domain.Town;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TownResponseDTO {
    private final String townCode;
    private final String nm;
    private final String fullNm;
    private final String engNm;
    private final BigDecimal lat;
    private final BigDecimal lng;
    private final Double zoomLevel;

    public static TownResponseDTO toDTO(final Town town) {
        return TownResponseDTO.builder()
                .townCode(town.getTownCode())
                .nm(town.getNm())
                .fullNm(town.getFullNm())
                .engNm(town.getEngNm())
                .lat(town.getLat())
                .lng(town.getLng())
                .zoomLevel(town.getZoomLevel())
                .build();
    }
}
