package com.example.mycodeBack.member.dto.request;

import com.example.mycodeBack.code.dto.request.CodeCategoryRequestDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MapDTO {
    private final double northEastLat;
    private final double northEastLng;
    private final double southWestLat;
    private final double southWestLng;
    private final List<CodeCategoryRequestDTO> itemsInHobbyFilter;
}
