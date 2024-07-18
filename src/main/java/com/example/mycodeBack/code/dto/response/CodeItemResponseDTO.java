package com.example.mycodeBack.code.dto.response;

import com.example.mycodeBack.code.domain.CodeItem;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CodeItemResponseDTO {
    private Long id;
    private String title;
    private String info;

    public static CodeItemResponseDTO toDTO(CodeItem codeItem) {
        return CodeItemResponseDTO.builder()
                .id(codeItem.getId())
                .title(codeItem.getTitle())
                .info(codeItem.getInfo())
                .build();
    }
}
