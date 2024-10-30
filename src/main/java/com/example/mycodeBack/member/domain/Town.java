package com.example.mycodeBack.member.domain;

import com.example.mycodeBack.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Town extends BaseEntity {
    @Id
    private String townCode;
    private String nm;
    private String fullNm;
    private String engNm;
    private BigDecimal lat;
    private BigDecimal lng;
    private Double zoomLevel;
}
