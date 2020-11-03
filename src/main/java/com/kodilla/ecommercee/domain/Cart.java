package com.kodilla.ecommercee.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CARTS")
public class Cart {

    @Id
    @GeneratedValue
    @Column(name = "CART_ID")
    private Long cartId;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "CART_PRODUCT",
            joinColumns = {@JoinColumn(name = "CART_ID", referencedColumnName = "CART_ID")},
            inverseJoinColumns = {@JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID")}
    )
    private List<Product> products = new ArrayList<>();

    @OneToOne(mappedBy = "cart", cascade = CascadeType.PERSIST)
    private User user;

    public Cart(List<Product> products) {
        this.products = products;
    }

}
