package com.example.mycodeBack.code.dto.request;

import com.example.mycodeBack.code.domain.CodeCategory;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CodeCategoryRequestDTO {
    private Long id;
    private String title;
    private String info;
    private List<CodeItemRequestDTO> codeItems;

    public static CodeCategoryRequestDTO toDTO(CodeCategory codeCategory) {
        return CodeCategoryRequestDTO.builder()
                .id(codeCategory.getId())
                .title(codeCategory.getTitle())
                .info(codeCategory.getInfo())
                .codeItems(codeCategory.getCodeItems().stream().map(CodeItemRequestDTO::toDTO).toList())
                .build();
    }
}
