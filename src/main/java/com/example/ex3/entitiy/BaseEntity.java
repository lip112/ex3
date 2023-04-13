package com.example.ex3.entitiy;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass//해당 어노테이션이 적용된 클래스는 테이블로 생성되지 않는다. 하지만 상속받은 테이블은 생성된다. 중복제거
@EntityListeners(value = {AuditingEntityListener.class})  //JPA내부에서 엔티티객체가 생성/변경되는것을 감지
@Getter
abstract class BaseEntity {
    @CreatedDate
    @Column(name = "regdate", updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "moddate")
    private LocalDateTime modDate;
}
