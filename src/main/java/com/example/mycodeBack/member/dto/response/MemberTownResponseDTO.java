package com.example.mycodeBack.member.dto.response;

import com.example.mycodeBack.member.domain.Town;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberTownResponseDTO {
    private final String id;
    private final String title;
    private final double x;
    private final double y;

    public static MemberTownResponseDTO toDTO(final Town town) {
        return MemberTownResponseDTO.builder()
                .id(town.getId())
                .title(town.getTitle())
                .x(town.getX())
                .y(town.getY())
                .build();
    }
}
