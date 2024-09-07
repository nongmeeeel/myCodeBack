package com.example.mycodeBack.member;

import com.example.mycodeBack.common.config.auth.CustomUser;
import com.example.mycodeBack.member.domain.Member;
import com.example.mycodeBack.member.dto.request.TownRequestDTO;
import com.example.mycodeBack.member.dto.response.MemberResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {

    final MemberService memberService;

    @GetMapping("/member")
    public ResponseEntity<MemberResponseDTO> getMember(Member thisMember) {
        MemberResponseDTO member = MemberResponseDTO.toDTO(thisMember);
        return ResponseEntity.ok()
                .body(member);
    }

    @GetMapping("/list")
    public ResponseEntity<List<MemberResponseDTO>> getMemberList(Member thisMember, Authentication authentication) {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();

        List<MemberResponseDTO> memberList = memberService.selectMemberList();
        return ResponseEntity.ok()
                .body(memberList);
    }

    @PutMapping("/town")
    public ResponseEntity<Void> updateMemberTown(@RequestBody TownRequestDTO townRequestDTO, Principal principal, Member thisMember) {
        memberService.updateMemberTown(townRequestDTO, thisMember.getId());

        return ResponseEntity.noContent()
                .build();
    }

    @PostMapping("/town")
    public ResponseEntity<Void> insertTownTemp(@RequestBody TownRequestDTO townRequestDTO, Principal principal) {
        memberService.insertTownTemp(townRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/filter")
    public ResponseEntity<Void> insertMemberCodeFilterMap(@RequestBody List<Long> itemIdList, Member thisMember) {
        memberService.insertMemberCodeFilterMap(itemIdList, thisMember);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> sendAuthCodeToServer(@RequestBody String authId) {

        String KAKAO_TOKEN_URL = "https://kauth.kakao.com/oauth/token";
        String CLIENT_ID = "38d56bcf00c8417e2f08d3843c3bc49e";
        String REDIRECT_URI = "http://10.0.2.2:8080/api/v1/member/auth";
        String CODE = authId;

        System.out.println(CODE);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", CLIENT_ID);
        body.add("redirect_uri", REDIRECT_URI);
        body.add("code", CODE);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                KAKAO_TOKEN_URL,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        HttpStatusCode statusCode = responseEntity.getStatusCode();

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/auth")
    public ResponseEntity<Void> getKakaoToken(@RequestBody int authId) {

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
