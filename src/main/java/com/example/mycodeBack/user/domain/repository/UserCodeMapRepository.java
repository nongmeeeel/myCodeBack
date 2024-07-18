package com.example.mycodeBack.user.domain.repository;

import com.example.mycodeBack.user.domain.UserCodeMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCodeMapRepository extends JpaRepository<UserCodeMap, Long> {
}
