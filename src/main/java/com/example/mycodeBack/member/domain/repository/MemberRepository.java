package com.example.mycodeBack.member.domain.repository;

import com.example.mycodeBack.code.domain.CodeType;
import com.example.mycodeBack.member.domain.Member;
import com.example.mycodeBack.member.dto.response.MemberCodeResponseDTO;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findAllByUseYn(String useYn);

    Optional<Member> findByEmail(String email);

    Member findByRefreshToken(String refreshToken);

    @Query("""
    SELECT m 
    FROM Member m 
    JOIN m.town t 
    LEFT JOIN m.memberCodeMap mcm
    LEFT JOIN mcm.codeItem c
    WHERE t.y BETWEEN :southWestLat AND :northEastLat
    AND t.x BETWEEN :southWestLng AND :northEastLng
    AND m.id != :thisId
    AND (
        NOT EXISTS (
            SELECT 1
            FROM Member m2
            JOIN m2.memberCodeFilterMap mcfm
            WHERE m2.id = :thisId
        ) OR 
        c.id IN (
            SELECT c2.id
            FROM Member m2
            LEFT JOIN m2.memberCodeFilterMap mcfm
            JOIN mcfm.codeItem c2
            WHERE m2.id = :thisId
        )
    )
""")
    List<Member> selectMemberListByMap(
            @Param("southWestLat") double southWestLat,
            @Param("northEastLat") double northEastLat,
            @Param("southWestLng") double southWestLng,
            @Param("northEastLng") double northEastLng,
            @Param("thisId") Long thisId
    );



//    @EntityGraph(attributePaths = {
//            "memberCodeMap",                 // Member -> MemberCodeMap
//            "memberCodeMap.codeItem",        // MemberCodeMap -> CodeItem
//            "memberCodeMap.codeItem.codeCategory", // CodeItem -> CodeCategory
//            "memberCodeMap.codeItem.codeCategory.codeType" // CodeCategory -> CodeType
//    })
    @Query("""
        SELECT new com.example.mycodeBack.member.dto.response.MemberCodeResponseDTO(ci.id, ci.title, cc.id, cc.title, ct.id, ct.title)
        FROM Member m
        JOIN m.memberCodeMap mcm
        JOIN mcm.codeItem ci
        JOIN ci.codeCategory cc
        JOIN cc.codeType ct
        WHERE m.id = :memberId
    """)
    List<MemberCodeResponseDTO> findCodeListByMemberId(@Param("memberId") Long memberId);

    @Query("""
        SELECT new com.example.mycodeBack.member.dto.response.MemberCodeResponseDTO(ci.id, ci.title, cc.id, cc.title, ct.id, ct.title)
        FROM Member m
        JOIN m.memberCodeFilterMap mcm
        JOIN mcm.codeItem ci
        JOIN ci.codeCategory cc
        JOIN cc.codeType ct
        WHERE m.id = :memberId
    """)
    List<MemberCodeResponseDTO> findCodeFilterListByMemberId(@Param("memberId") Long memberId);

}
