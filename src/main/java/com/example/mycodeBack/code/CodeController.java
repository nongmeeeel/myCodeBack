package com.example.mycodeBack.code;

import com.example.mycodeBack.code.dto.response.CodeTypeResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/code")
@RequiredArgsConstructor
public class CodeController {

    final CodeService codeService;

    // 코드 목록 조회
    @GetMapping("/list")
    public ResponseEntity<List<CodeTypeResponseDTO>> getMemberList() {
        List<CodeTypeResponseDTO> allCodeList = codeService.getAllCodeList();
        return ResponseEntity.ok()
                .body(allCodeList);
    }
}
