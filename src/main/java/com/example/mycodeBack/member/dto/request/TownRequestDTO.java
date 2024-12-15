package com.example.mycodeBack.member.dto.request;

import com.example.mycodeBack.member.domain.Town;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TownRequestDTO {
    private final String id;
    private final String title;
    private final double x;
    private final double y;

    public Town toEntity() {
        return Town.builder()
                .id(this.id)
                .title(this.title)
                .x(this.x)
                .y(this.y)
                .build();
    }
}
