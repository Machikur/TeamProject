package com.kodilla.ecommercee.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    private long userId;

    @Column(name = "USER_KEY")
    private String userKey = null;

    @NotNull
    @Column(name = "USERNAME")
    private String username;

    @NotNull
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
    private LocalDateTime keyTimeCreated = null;

    public User(long userId, @NotNull String username, @NotNull String password, Cart cart, List<Order> orders) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.cart = cart;
        this.orders = orders;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
        setKeyTimeCreated(LocalDateTime.now());
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    private void setKeyTimeCreated(LocalDateTime localDateTime) {
        this.keyTimeCreated = localDateTime;
    }
}