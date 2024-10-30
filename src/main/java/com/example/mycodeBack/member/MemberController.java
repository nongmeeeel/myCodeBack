package com.example.mycodeBack.member;

import com.example.mycodeBack.common.config.auth.CustomUser;
import com.example.mycodeBack.member.dto.request.MapDTO;
import com.example.mycodeBack.member.dto.request.TownRequestDTO;
import com.example.mycodeBack.member.dto.response.MemberResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    final MemberService memberService;

    @GetMapping("/fetch")
    public ResponseEntity<MemberResponseDTO> fetchMember(Authentication authentication) {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();

        MemberResponseDTO memberResponseDTO = memberService.fetchMember(customUser.getEmail());
        return ResponseEntity.ok()
                .body(memberResponseDTO);
    }

    // 멤버 리스트 조회
    @GetMapping("/list")
    public ResponseEntity<List<MemberResponseDTO>> getMemberList(Authentication authentication) {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();

        List<MemberResponseDTO> memberList = memberService.selectMemberList();
        return ResponseEntity.ok()
                .body(memberList);
    }

    @PutMapping("/list/by-map")
    public ResponseEntity<List<MemberResponseDTO>> updateFilterAndSelectMemberListByMap(@RequestBody MapDTO mapDTO) {
//        List<Object> itemsInHobbyFilter = (List<Object>) mapData.get("itemsInHobbyFilter");
//        double northEastLat = (double)mapData.get("northEastLat");
//        double northEastLng = (double)mapData.get("northEastLng");
//        double southWestLat = (double)mapData.get("southWestLat");
//        double southWestLng = (double)mapData.get("southWestLng");

//        memberService.updateFilter(itemsInHobbyFilter);
        List<MemberResponseDTO> memberList = null;
        return ResponseEntity.ok()
                .body(memberList);
    }

    // 내 동네 UPDATE
    @PutMapping("/town")
    public ResponseEntity<Void> updateMemberTown(@RequestBody TownRequestDTO townRequestDTO, Authentication authentication) {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        memberService.updateMemberTown(townRequestDTO, customUser.getEmail());

        return ResponseEntity.noContent()
                .build();
    }

    // 지도 탐색 시 필터할 코드 UPDATE
    @PutMapping("/code/filter")
    public ResponseEntity<Void> updateMemberCodeFilterMap(@RequestBody List<Long> itemIdList, Authentication authentication ) {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();

        memberService.updateMemberCodeFilterMap(itemIdList, customUser.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 임시 : town 추가
    @PostMapping("/town")
    public ResponseEntity<Void> insertTownTemp(@RequestBody TownRequestDTO townRequestDTO, Principal principal) {
        memberService.insertTownTemp(townRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
