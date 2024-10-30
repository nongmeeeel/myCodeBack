package com.example.mycodeBack.code.dto.request;

import com.example.mycodeBack.code.domain.CodeType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CodeTypeRequestDTO {
    private Long id;
    private String title;
    private String info;
    private List<CodeCategoryRequestDTO> codeCategories;

    public static CodeTypeRequestDTO toDTO(CodeType codeType) {
        return CodeTypeRequestDTO.builder()
                .id(codeType.getId())
                .title(codeType.getTitle())
                .info(codeType.getInfo())
                .codeCategories(codeType.getCodeCategories().stream().map(CodeCategoryRequestDTO::toDTO).toList())
                .build();
    }
}
