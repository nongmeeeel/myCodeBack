package com.example.mycodeBack.member.domain.repository;

import com.example.mycodeBack.member.domain.Member;
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



//    @EntityGraph(attributePaths = {"roleList"})
//    @Query("select m from Member m where m.email = :email")
//    Member getWithRoles(@Param("email") String email);
}
