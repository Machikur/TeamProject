package com.kodilla.ecommercee.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "USERS", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
public class User {

    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "USER_KEY")
    private String userKey;

    @NotBlank
    @Column(name = "USERNAME", unique = true)
    private String username;

    @NotBlank
    @Column(name = "PASSWORD")
    private String password;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id")
    private Cart cart;
    @OneToMany(

            targetEntity = Order.class,
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Order> orders = new ArrayList<>();

    @Column(name = "IS_ENABLE")
    private boolean isEnable = true;

    @Column(name = "KEY_TIME_CREATED")
    private LocalDateTime keyTimeCreated;

    public User(@Size(min = 3) String username,
                @Size(min = 3) String password) {
        this.username = username;
        this.password = password;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
        setKeyTimeCreated(LocalDateTime.now());
    }

    private void setKeyTimeCreated(LocalDateTime localDateTime) {
        this.keyTimeCreated = localDateTime;
    }
}