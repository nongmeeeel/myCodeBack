package com.example.mycodeBack.code.domain.repository;

import com.example.mycodeBack.code.domain.CodeItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeItemRepository extends JpaRepository<CodeItem, Long> {
}
