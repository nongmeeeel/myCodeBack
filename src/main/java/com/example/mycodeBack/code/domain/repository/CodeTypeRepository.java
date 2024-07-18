package com.example.mycodeBack.code.domain.repository;

import com.example.mycodeBack.code.domain.CodeItem;
import com.example.mycodeBack.code.domain.CodeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeTypeRepository extends JpaRepository<CodeType, Long> {
}
