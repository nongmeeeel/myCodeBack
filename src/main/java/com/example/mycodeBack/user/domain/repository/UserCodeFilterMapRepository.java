package com.example.mycodeBack.user.domain.repository;

import com.example.mycodeBack.user.domain.User;
import com.example.mycodeBack.user.domain.UserCodeFilterMap;
import com.example.mycodeBack.user.domain.UserCodeMap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCodeFilterMapRepository extends JpaRepository<UserCodeFilterMap, Long> {
    void deleteByUser(User user);
}
