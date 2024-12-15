package com.example.mycodeBack.member.dto.response;

import com.example.mycodeBack.code.dto.response.CodeTypeResponseDTO;
import com.example.mycodeBack.member.domain.Member;
import com.example.mycodeBack.member.domain.type.MemberRole;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FetchMemberResponseDTO {

    private MemberResponseDTO memberResponseDTO;
    private List<MemberCodeResponseDTO> MemberCodeResponseDTOList;

    public static FetchMemberResponseDTO toDTO(MemberResponseDTO memberResponseDTO, List<MemberCodeResponseDTO> MemberCodeResponseDTO) {
        return FetchMemberResponseDTO.builder()
                .memberResponseDTO(memberResponseDTO)
                .MemberCodeResponseDTOList(MemberCodeResponseDTO)
                .build();
    }
}
