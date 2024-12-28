package com.example.mycodeBack.member.domain.repository;

import com.example.mycodeBack.member.domain.Member;
import com.example.mycodeBack.member.domain.MemberCodeFilterMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberCodeFilterMapRepository extends JpaRepository<MemberCodeFilterMap, Long> {
    void deleteByMember(Member member);
}
