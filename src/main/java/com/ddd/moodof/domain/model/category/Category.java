package com.ddd.moodof.domain.model.category;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
@Entity
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long previousId;

    private String title;

    private Long userId;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    public void updateTitle(String title){
        this.title = title;
    }

    public Category updatePreviousId(Long previousId) {
        validationPreviousId(previousId);
        this.previousId = previousId;
        return this;
    }

    private void validationPreviousId(Long previousId) {
        if (this.previousId.equals(previousId)) throw new IllegalArgumentException("동일한 previousId: " + previousId);
    }

    public boolean isNotEqual(Long userId) {
        return !this.userId.equals(userId);
    }
}
