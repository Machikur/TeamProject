package com.kodilla.ecommercee.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "USER_OPERATIONS")
@NoArgsConstructor
@Getter
@Setter
public class UserOperation {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @OneToOne(targetEntity = User.class,
            cascade = CascadeType.MERGE,
            fetch = FetchType.LAZY)
    private User user;

    @Column(name = "OPERATION_NAME")
    private String operationName;

    @Column(name = "OPERATION_TIME")
    private LocalDateTime operationTime;

    public UserOperation(String operationName) {
        this.operationName = operationName;
    }

    @PrePersist
    void setTime() {
        this.operationTime = LocalDateTime.now();
    }
}
