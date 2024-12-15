package com.example.mycodeBack.member.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberCodeResponseDTO {
    private Long codeItemId;
    private String codeItemTitle;
    private Long codeCategoryId;
    private String codeCategoryTitle;
    private Long codeTypeId;
    private String codeTypeTitle;

    public MemberCodeResponseDTO(Long codeItemId, String codeItemTitle, Long codeCategoryId, String codeCategoryTitle, Long codeTypeId, String codeTypeTitle) {
        this.codeItemId = codeItemId;
        this.codeItemTitle = codeItemTitle;
        this.codeCategoryId = codeCategoryId;
        this.codeCategoryTitle = codeCategoryTitle;
        this.codeTypeId = codeTypeId;
        this.codeTypeTitle = codeTypeTitle;
    }
}
