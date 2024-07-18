package com.example.mycodeBack.user.domain.repository;

import com.example.mycodeBack.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByUseYn(String useYn);
}
