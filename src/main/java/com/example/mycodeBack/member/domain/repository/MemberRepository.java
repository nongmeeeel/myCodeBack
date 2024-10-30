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

    @Query("SELECT m FROM Member m JOIN m.town t WHERE t.lat BETWEEN :southWestLat AND :northEastLat AND t.lng BETWEEN :southWestLng AND :northEastLng")
    List<Member> selectMemberListByMap(
            @Param("southWestLat") double southWestLat,
            @Param("northEastLat") double northEastLat,
            @Param("southWestLng") double southWestLng,
            @Param("northEastLng") double northEastLng
    );



//    @EntityGraph(attributePaths = {"roleList"})
//    @Query("select m from Member m where m.email = :email")
//    Member getWithRoles(@Param("email") String email);
}
