package com.example.mycodeBack.code.dto.response;

import com.example.mycodeBack.code.domain.CodeCategory;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CodeCategoryResponseDTO {
    private Long id;
    private String title;
    private String info;
    private List<CodeItemResponseDTO> codeItems;

    public static CodeCategoryResponseDTO toDTO(CodeCategory codeCategory) {
        return CodeCategoryResponseDTO.builder()
                .id(codeCategory.getId())
                .title(codeCategory.getTitle())
                .info(codeCategory.getInfo())
                .codeItems(codeCategory.getCodeItems().stream().map(CodeItemResponseDTO::toDTO).toList())
                .build();
    }
}
