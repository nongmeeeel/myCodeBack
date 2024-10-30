package com.example.mycodeBack.code.dto.request;

import com.example.mycodeBack.code.domain.CodeItem;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CodeItemRequestDTO {
    private Long id;
    private String title;
    private String info;

    public static CodeItemRequestDTO toDTO(CodeItem codeItem) {
        return CodeItemRequestDTO.builder()
                .id(codeItem.getId())
                .title(codeItem.getTitle())
                .info(codeItem.getInfo())
                .build();
    }
}
