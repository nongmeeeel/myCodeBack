package com.example.mycodeBack.code.domain.repository;

import com.example.mycodeBack.code.domain.CodeItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CodeItemRepository extends JpaRepository<CodeItem, Long> {
//    List<CodeItem> findAllById(Set<Long> codeItemIdSet);
}
