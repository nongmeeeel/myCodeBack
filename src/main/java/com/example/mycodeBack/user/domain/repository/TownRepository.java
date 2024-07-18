package com.example.mycodeBack.user.domain.repository;

import com.example.mycodeBack.user.domain.Town;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TownRepository extends JpaRepository<Town, String> {
}
