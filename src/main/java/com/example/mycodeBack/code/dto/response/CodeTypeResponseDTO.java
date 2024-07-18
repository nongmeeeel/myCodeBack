package com.example.mycodeBack.code.dto.response;

import com.example.mycodeBack.code.domain.CodeType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CodeTypeResponseDTO {
    private Long id;
    private String title;
    private String info;
    private List<CodeCategoryResponseDTO> codeCategories;

    public static CodeTypeResponseDTO toDTO(CodeType codeType) {
        return CodeTypeResponseDTO.builder()
                .id(codeType.getId())
                .title(codeType.getTitle())
                .info(codeType.getInfo())
                .codeCategories(codeType.getCodeCategories().stream().map(CodeCategoryResponseDTO::toDTO).toList())
                .build();
    }
}
