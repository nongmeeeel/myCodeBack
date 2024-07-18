package com.example.mycodeBack.code.domain.repository;

import com.example.mycodeBack.code.domain.CodeCategory;
import com.example.mycodeBack.code.domain.CodeItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeCategoryRepository extends JpaRepository<CodeCategory, Long> {
}
