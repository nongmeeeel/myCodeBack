package com.example.mycodeBack.code;

import com.example.mycodeBack.code.domain.CodeItem;
import com.example.mycodeBack.code.domain.CodeType;
import com.example.mycodeBack.code.domain.repository.CodeItemRepository;
import com.example.mycodeBack.code.domain.repository.CodeTypeRepository;
import com.example.mycodeBack.code.dto.response.CodeItemResponseDTO;
import com.example.mycodeBack.code.dto.response.CodeTypeResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CodeService {
    private final CodeItemRepository codeItemRepository;
    private final CodeTypeRepository codeTypeRepository;

    public List<CodeTypeResponseDTO> getAllCodeList() {
        List<CodeType> allCodeList = codeTypeRepository.findAll();
        return allCodeList.stream()
                .map(CodeTypeResponseDTO::toDTO)
                .toList();
    }

}
