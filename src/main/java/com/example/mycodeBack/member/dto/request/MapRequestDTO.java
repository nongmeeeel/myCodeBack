package com.example.mycodeBack.member.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MapRequestDTO {
    private final double northEastLat;
    private final double northEastLng;
    private final double southWestLat;
    private final double southWestLng;
}
