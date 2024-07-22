package com.example.mycodeBack.user.domain;

import com.example.mycodeBack.code.domain.CodeItem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class UserCodeFilterMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "code_item_id")
    private CodeItem codeItem;
    private String useYn;
    @CreatedDate
    private LocalDateTime insertDate = LocalDateTime.now();;
    @LastModifiedDate
    private LocalDateTime modifyDate;
    private String modifyBy;

}
