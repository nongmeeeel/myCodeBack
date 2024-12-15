package com.example.mycodeBack.member.domain.repository;

import com.example.mycodeBack.member.domain.Member;
import com.example.mycodeBack.member.domain.MemberCodeMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberCodeMapRepository extends JpaRepository<MemberCodeMap, Long> {

    void deleteByMemberId(Long memberId);

    List<MemberCodeMap> findAllByMemberId(Long id);
}
