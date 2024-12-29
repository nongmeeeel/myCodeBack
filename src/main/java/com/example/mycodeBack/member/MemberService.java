package com.example.mycodeBack.member;

import com.example.mycodeBack.code.domain.CodeItem;
import com.example.mycodeBack.code.domain.CodeType;
import com.example.mycodeBack.code.domain.repository.CodeItemRepository;
import com.example.mycodeBack.code.dto.response.CodeTypeResponseDTO;
import com.example.mycodeBack.common.config.auth.JWTUtil;
import com.example.mycodeBack.common.exception.type.UserNotFoundException;
import com.example.mycodeBack.member.domain.MemberCodeMap;
import com.example.mycodeBack.member.domain.Town;
import com.example.mycodeBack.member.domain.Member;
import com.example.mycodeBack.member.domain.MemberCodeFilterMap;
import com.example.mycodeBack.member.domain.repository.MemberCodeMapRepository;
import com.example.mycodeBack.member.domain.repository.TownRepository;
import com.example.mycodeBack.member.domain.repository.MemberCodeFilterMapRepository;
import com.example.mycodeBack.member.domain.repository.MemberRepository;
import com.example.mycodeBack.member.domain.type.MemberRole;
import com.example.mycodeBack.member.dto.common.TokenDTO;
import com.example.mycodeBack.member.dto.request.MapRequestDTO;
import com.example.mycodeBack.member.dto.request.SignUpRequestDTO;
import com.example.mycodeBack.member.dto.request.TownRequestDTO;
import com.example.mycodeBack.member.dto.request.UpdateMemberRequestDTO;
import com.example.mycodeBack.member.dto.response.FetchMemberResponseDTO;
import com.example.mycodeBack.member.dto.response.MemberCodeResponseDTO;
import com.example.mycodeBack.member.dto.response.MemberResponseDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.mycodeBack.common.exception.type.ExceptionCode.NOT_FOUND_USER_ID;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final TownRepository townRepository;
    private final CodeItemRepository codeItemRepository;
    private final MemberCodeFilterMapRepository memberCodeFilterMapRepository;
    private final MemberCodeMapRepository memberCodeMapRepository;
    private final JWTUtil jwtUtil;

//    public UserResponseDTO selectUser() {
//        Long tempUserId = 1L;
//        User user = userRepository.findById(tempUserId).orElseThrow();
//        return UserResponseDTO.toDTO(user);
//    }

    @Transactional
    public void checkSignUpKakaoEmail(String username, String password, String loginType, String nickname) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        Member member = memberRepository.findByEmail(username).orElse(null);
        if(member == null) {
            Member newMember = Member.builder()
                    .email(username)
                    .pw(bCryptPasswordEncoder.encode(password))
                    .role(MemberRole.GUEST)
                    .loginType(loginType)
                    .kakaoNickname(nickname)
                    .build();
            memberRepository.save(newMember);
        }
    }

    public FetchMemberResponseDTO fetchMember(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(NOT_FOUND_USER_ID));
        MemberResponseDTO memberResponseDTO = MemberResponseDTO.toDTO(member);

        Long memberId = member.getId();
        List<MemberCodeResponseDTO> memberCodeResponseDTOList = memberRepository.findCodeListByMemberId(memberId);
        List<MemberCodeResponseDTO> memberCodeFilterResponseDTOList = memberRepository.findCodeFilterListByMemberId(memberId);

        return FetchMemberResponseDTO.toDTO(memberResponseDTO, memberCodeResponseDTOList, memberCodeFilterResponseDTOList);
    }

    public List<MemberResponseDTO> selectMemberList() {
        List<Member> memberList = memberRepository.findAllByUseYn("Y");
        return memberList.stream()
                .map(member -> MemberResponseDTO.toDTO(member))
                .collect(Collectors.toList());
    }

    public List<MemberResponseDTO> selectMemberListByMapInfoAPI(MapRequestDTO mapRequestDTO) {

        return null;
    }

    public List<FetchMemberResponseDTO> selectMemberListByMap(MapRequestDTO mapRequestDTO, Long thisId) {
        List<FetchMemberResponseDTO> fetchMemberResponseDTOList = new ArrayList<>();

        List<Member> memberList = memberRepository.selectMemberListByMap(
                mapRequestDTO.getSouthWestLat()
                , mapRequestDTO.getNorthEastLat()
                , mapRequestDTO.getSouthWestLng()
                , mapRequestDTO.getNorthEastLng()
                , thisId
        );

        for(Member member : memberList){
            MemberResponseDTO memberResponseDTO = MemberResponseDTO.toDTO(member);
            List<MemberCodeResponseDTO> memberCodeResponseDTOList = memberRepository.findCodeListByMemberId(member.getId());
            fetchMemberResponseDTOList.add(FetchMemberResponseDTO.toListDTO(memberResponseDTO, memberCodeResponseDTOList));
        }

        return fetchMemberResponseDTOList;
    }

    @Transactional
    public void updateMemberTown(TownRequestDTO townRequestDTO, String email) {
        Town town = townRequestDTO.toEntity();

        Optional<Town> OpTown = townRepository.findById(town.getId());
        if (!OpTown.isPresent()) {
            townRepository.saveAndFlush(town);
        }

        Member member = memberRepository.findByEmail(email).orElseThrow();
        member.updateTownCode(town);
    }

//    @Transactional
//    public void updateMemberCodeFilterMap(List<Long> itemIdList, String email) {
//        Member member = memberRepository.findByEmail(email)
//                .orElseThrow(() -> new UserNotFoundException(NOT_FOUND_USER_ID));
//        memberCodeFilterMapRepository.deleteByMember(member);
//
//        List<CodeItem> codeItems = codeItemRepository.findAllById(itemIdList);
//        // MemberCodeFilterMap 리스트 생성
//        List<MemberCodeFilterMap> memberCodeFilterMapList = codeItems.stream()
//                .map(codeItem -> {
//                    return MemberCodeFilterMap.builder()
//                            .member(member)
//                            .codeItem(codeItem)
//                            .build();
//                }).collect(Collectors.toList());
//
//        memberCodeFilterMapRepository.saveAll(memberCodeFilterMapList);
//    }

    @Transactional
    public void updateRefreshToken(String username, String refreshToken) {
        memberRepository.findByEmail(username)
                .ifPresentOrElse(
                        member -> member.updateRefreshToken(refreshToken),
                        () -> new UserNotFoundException(NOT_FOUND_USER_ID)
                );
    }

    @Transactional
    public TokenDTO signUp(SignUpRequestDTO signUpRequestDTO, String email, Long id) {
        memberRepository.findByEmail(email)
                .ifPresentOrElse(
                        member -> member.signUp(
                                signUpRequestDTO.getName(),
                                signUpRequestDTO.getGender(),
                                signUpRequestDTO.getBirthDate()
                        ),
                        () -> new UserNotFoundException(NOT_FOUND_USER_ID)
                );
        String accessToken = jwtUtil.createAccessToken(email, "MEMBER", id);
        String refreshToken = jwtUtil.createRefreshToken();

        updateRefreshToken(email, refreshToken);

        TokenDTO tokenDTO = new TokenDTO(accessToken, refreshToken);

        return tokenDTO;
    }












    // 임시 : town 추가
    @Transactional
    public void insertTownTemp(TownRequestDTO townRequestDTO) {
        Town town = townRequestDTO.toEntity();

        Optional<Town> townOp = townRepository.findById(town.getId());
        if (!townOp.isPresent()) {
            townRepository.saveAndFlush(town);
            System.out.println("저장완료 : " + town.getId());
        }
    }

    @Transactional
    public void updateMemberCodeMap(List<Long> codeItemIdSet, Long thisId) {
        Member member = memberRepository.findById(thisId)
                .orElseThrow(() -> new UserNotFoundException(NOT_FOUND_USER_ID));
        List<CodeItem> CodeItemList = codeItemRepository.findAllById(codeItemIdSet);

        memberCodeMapRepository.deleteByMemberId(member.getId());

        List<MemberCodeMap> memberCodeMapList = CodeItemList.stream()
                .map(codeItem -> MemberCodeMap.builder()
                        .member(member)
                        .codeItem(codeItem)
                        .build())
                .toList();

        memberCodeMapRepository.saveAll(memberCodeMapList);
    }

    @Transactional
    public void updateMemberCodeFilterMap(List<Long> codeItemIdSet, Long thisId) {
        Member member = memberRepository.findById(thisId)
                .orElseThrow(() -> new UserNotFoundException(NOT_FOUND_USER_ID));
        List<CodeItem> CodeItemList = codeItemRepository.findAllById(codeItemIdSet);

        memberCodeFilterMapRepository.deleteByMemberId(member.getId());

        List<MemberCodeFilterMap> memberCodeFilterMapList = CodeItemList.stream()
                .map(codeItem -> MemberCodeFilterMap.builder()
                        .member(member)
                        .codeItem(codeItem)
                        .build())
                .toList();

        memberCodeFilterMapRepository.saveAll(memberCodeFilterMapList);
    }

    public List<MemberCodeResponseDTO> selectMemberCodeList(Long thisId) {
        Member member = memberRepository.findById(thisId)
                .orElseThrow(() -> new UserNotFoundException(NOT_FOUND_USER_ID));

        List<MemberCodeResponseDTO> memberCodeResponseDTOList = memberRepository.findCodeListByMemberId(member.getId());

        return memberCodeResponseDTOList;
    }

    public List<MemberCodeResponseDTO> selectMemberCodeFilterList(Long thisId) {
        Member member = memberRepository.findById(thisId)
                .orElseThrow(() -> new UserNotFoundException(NOT_FOUND_USER_ID));

        List<MemberCodeResponseDTO> memberCodeResponseDTOList = memberRepository.findCodeFilterListByMemberId(member.getId());

        return memberCodeResponseDTOList;
    }

    @Transactional
    public MemberResponseDTO updateMember(UpdateMemberRequestDTO updateMemberRequestDTO, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(NOT_FOUND_USER_ID));

        member.updateMember(updateMemberRequestDTO.getName(), updateMemberRequestDTO.getGender(), updateMemberRequestDTO.getBirthDate());
        return MemberResponseDTO.toDTO(member);
    }
}
