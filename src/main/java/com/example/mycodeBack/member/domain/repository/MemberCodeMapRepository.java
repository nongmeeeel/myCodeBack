package com.example.mycodeBack.member.domain.repository;

import com.example.mycodeBack.member.domain.MemberCodeMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberCodeMapRepository extends JpaRepository<MemberCodeMap, Long> {
}
