package com.example.mycodeBack.member;

import com.example.mycodeBack.code.domain.CodeItem;
import com.example.mycodeBack.code.domain.repository.CodeItemRepository;
import com.example.mycodeBack.common.exception.type.UserNotFoundException;
import com.example.mycodeBack.member.domain.Town;
import com.example.mycodeBack.member.domain.Member;
import com.example.mycodeBack.member.domain.MemberCodeFilterMap;
import com.example.mycodeBack.member.domain.repository.TownRepository;
import com.example.mycodeBack.member.domain.repository.MemberCodeFilterMapRepository;
import com.example.mycodeBack.member.domain.repository.MemberRepository;
import com.example.mycodeBack.member.dto.request.TownRequestDTO;
import com.example.mycodeBack.member.dto.response.MemberResponseDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.mycodeBack.common.exception.type.ExceptionCode.NOT_FOUND_USER_ID;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final TownRepository townRepository;
    private final CodeItemRepository codeItemRepository;
    private final MemberCodeFilterMapRepository memberCodeFilterMapRepository;

//    public UserResponseDTO selectUser() {
//        Long tempUserId = 1L;
//        User user = userRepository.findById(tempUserId).orElseThrow();
//        return UserResponseDTO.toDTO(user);
//    }

    public MemberResponseDTO fetchMember(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(NOT_FOUND_USER_ID));
        return MemberResponseDTO.toDTO(member);
    }

    public List<MemberResponseDTO> selectMemberList() {
        List<Member> memberList = memberRepository.findAllByUseYn("Y");
        return memberList.stream()
                .map(member -> MemberResponseDTO.toDTO(member))
                .collect(Collectors.toList());
    }

    public void updateFilter(List<Object> itemsInHobbyFilter) {

    }

    public List<MemberResponseDTO> selectMemberListByMap(double northEastLat, double northEastLng, double southWestLat, double southWestLng) {
        List<Member> memberList = memberRepository.selectMemberListByMap(
                southWestLat, northEastLat, southWestLng, northEastLng
        );
        return memberList.stream()
                .map(member -> MemberResponseDTO.toDTO(member))
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateMemberTown(TownRequestDTO townRequestDTO, String email) {
        Town town = TownRequestDTO.toEntity(townRequestDTO);

        Optional<Town> townOp = townRepository.findById(town.getTownCode());
        if (!townOp.isPresent()) {
            townRepository.saveAndFlush(town);
        }

        Member member = memberRepository.findByEmail(email).orElseThrow();
        member.updateTownCode(town);
    }

    @Transactional
    public void updateMemberCodeFilterMap(List<Long> itemIdList, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(NOT_FOUND_USER_ID));
        memberCodeFilterMapRepository.deleteByMember(member);

        List<CodeItem> codeItems = codeItemRepository.findAllById(itemIdList);
        // MemberCodeFilterMap 리스트 생성
        List<MemberCodeFilterMap> memberCodeFilterMapList = codeItems.stream()
                .map(codeItem -> {
                    return MemberCodeFilterMap.builder()
                            .member(member)
                            .codeItem(codeItem)
                            .build();
                }).collect(Collectors.toList());

        memberCodeFilterMapRepository.saveAll(memberCodeFilterMapList);
    }

    @Transactional
    public void updateRefreshToken(String username, String refreshToken) {
        memberRepository.findByEmail(username)
                .ifPresentOrElse(
                        member -> member.updateRefreshToken(refreshToken),
                        () -> new UserNotFoundException(NOT_FOUND_USER_ID)
                );
    }




    // 임시 : town 추가
    @Transactional
    public void insertTownTemp(TownRequestDTO townRequestDTO) {
        Town town = TownRequestDTO.toEntity(townRequestDTO);

        Optional<Town> townOp = townRepository.findById(town.getTownCode());
        if (!townOp.isPresent()) {
            townRepository.saveAndFlush(town);
            System.out.println("저장완료 : " + town.getTownCode());
        }
    }



}
