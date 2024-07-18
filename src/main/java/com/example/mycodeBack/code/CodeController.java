package com.example.mycodeBack.code;

import com.example.mycodeBack.code.domain.CodeItem;
import com.example.mycodeBack.code.domain.CodeType;
import com.example.mycodeBack.code.domain.repository.CodeItemRepository;
import com.example.mycodeBack.code.domain.repository.CodeTypeRepository;
import com.example.mycodeBack.code.dto.response.CodeItemResponseDTO;
import com.example.mycodeBack.code.dto.response.CodeTypeResponseDTO;
import com.example.mycodeBack.user.dto.response.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/code")
@RequiredArgsConstructor
public class CodeController {

    final CodeService codeService;
    @GetMapping("/list")
    public ResponseEntity<List<CodeTypeResponseDTO>> getUserList() {
        List<CodeTypeResponseDTO> allCodeList = codeService.getAllCodeList();
        return ResponseEntity.ok()
                .body(allCodeList);
    }
}
