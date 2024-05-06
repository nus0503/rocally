package com.company.rocally.domain;

import com.company.rocally.common.Period;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(value = AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    @CreatedDate
    private String createdDate;

    @LastModifiedDate
    private String modifiedDate;

    /**
     * JPA 엔티티 콜백 메소드
     * 콜백 메소드는 void타입을 리턴해야 한다.
     */
    @PrePersist
    public void onPrePersist() {
        this.createdDate = LocalDateTime.now().format(Period.yyyyMMddHHmm);
        this.modifiedDate = createdDate;
    }

    @PreUpdate
    public void onPreUpdate() {
        this.modifiedDate = LocalDateTime.now().format(Period.yyyyMMddHHmm);
    }
}
