package com.example.mycodeBack.user.domain.repository;

import com.example.mycodeBack.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByUseYn(String useYn);
    Optional<User> findByKakaoId(String kakaoId);
}
