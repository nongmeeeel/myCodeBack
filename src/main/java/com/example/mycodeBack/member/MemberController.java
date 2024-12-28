package com.example.mycodeBack.member;

import com.example.mycodeBack.common.config.auth.CustomUser;
import com.example.mycodeBack.member.dto.common.TokenDTO;
import com.example.mycodeBack.member.dto.request.MapRequestDTO;
import com.example.mycodeBack.member.dto.request.SignUpRequestDTO;
import com.example.mycodeBack.member.dto.request.TownRequestDTO;
import com.example.mycodeBack.member.dto.request.UpdateMemberRequestDTO;
import com.example.mycodeBack.member.dto.response.FetchMemberResponseDTO;
import com.example.mycodeBack.member.dto.response.MemberCodeResponseDTO;
import com.example.mycodeBack.member.dto.response.MemberResponseDTO;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    final MemberService memberService;

    @GetMapping("/fetch")
    public ResponseEntity<FetchMemberResponseDTO> fetchMember(Authentication authentication) {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();

        FetchMemberResponseDTO fetchMemberResponseDTO = memberService.fetchMember(customUser.getEmail());
        return ResponseEntity.ok()
                .body(fetchMemberResponseDTO);
    }

    // 멤버 리스트 조회
    @GetMapping("/list")
    public ResponseEntity<List<MemberResponseDTO>> getMemberList(Authentication authentication) {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();

        List<MemberResponseDTO> memberList = memberService.selectMemberList();
        return ResponseEntity.ok()
                .body(memberList);
    }

    @GetMapping("/list/by-map")
    public ResponseEntity<List<MemberResponseDTO>> selectMemberListByMapInfoAPI(MapRequestDTO mapRequestDTO) {

        List<MemberResponseDTO> memberResponseDTOList = memberService.selectMemberListByMap(mapRequestDTO);
        return ResponseEntity.ok()
                .body(memberResponseDTOList);
    }

    // 내 동네 UPDATE
    @PutMapping("/town")
    public ResponseEntity<Void> updateMemberTown(@RequestBody TownRequestDTO townRequestDTO, Authentication authentication) {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        memberService.updateMemberTown(townRequestDTO, customUser.getEmail());

        return ResponseEntity.noContent()
                .build();
    }


    // 임시 : town 추가
    @PostMapping("/town")
    public ResponseEntity<Void> insertTownTemp(@RequestBody TownRequestDTO townRequestDTO, Principal principal) {
        memberService.insertTownTemp(townRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/signup")
    public ResponseEntity<Void> signUp(@RequestBody SignUpRequestDTO signUpRequestDTO, Authentication authentication, HttpServletResponse response) {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();

        TokenDTO tokenDTO = memberService.signUp(signUpRequestDTO, customUser.getEmail(), customUser.getId());
        memberService.updateMemberTown(signUpRequestDTO.getMemberTown(), customUser.getEmail());

        response.setHeader("Access-Token", tokenDTO.getAccessToken());
        response.setHeader("Refresh-Token", tokenDTO.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/code")
    public ResponseEntity<Void> updateMemberCodeMap(@RequestBody List<Long> codeItemIdSet, Authentication authentication) {
        CustomUser thisUser = (CustomUser) authentication.getPrincipal();

        memberService.updateMemberCodeMap(codeItemIdSet, thisUser.getId());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/code/filter")
    public ResponseEntity<Void> updateMemberCodeFilterMap(@RequestBody List<Long> codeItemIdSet, Authentication authentication) {
        CustomUser thisUser = (CustomUser) authentication.getPrincipal();

        memberService.updateMemberCodeFilterMap(codeItemIdSet, thisUser.getId());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/codes")
    public ResponseEntity<List<MemberCodeResponseDTO>> selectMemberCodeList(Authentication authentication) {
        CustomUser thisUser = (CustomUser) authentication.getPrincipal();

        List<MemberCodeResponseDTO> memberCodeResponseDTOList = memberService.selectMemberCodeList(thisUser.getId());

        return ResponseEntity.ok()
                .body(memberCodeResponseDTOList);
    }

    @GetMapping("/codes/filter")
    public ResponseEntity<List<MemberCodeResponseDTO>> selectMemberCodeFilterList(Authentication authentication) {
        CustomUser thisUser = (CustomUser) authentication.getPrincipal();

        List<MemberCodeResponseDTO> memberCodeResponseDTOList = memberService.selectMemberCodeFilterList(thisUser.getId());

        return ResponseEntity.ok()
                .body(memberCodeResponseDTOList);
    }

    @PutMapping("/")
    public ResponseEntity<MemberResponseDTO> updateMember(@RequestBody UpdateMemberRequestDTO updateMemberRequestDTO, Authentication authentication) {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();

        MemberResponseDTO memberResponseDTO = memberService.updateMember(updateMemberRequestDTO, customUser.getEmail());

        return ResponseEntity.ok()
                .body(memberResponseDTO);
    }
}
