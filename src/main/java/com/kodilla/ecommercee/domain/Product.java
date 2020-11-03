package com.kodilla.ecommercee.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "PRODUCTS")
public class Product {

    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "group_id")
    public Group group;

    @Id
    @GeneratedValue()
    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Column(name = "NAME", unique = true)
    private String productName;

    @Column(name = "PRICE")
    private double productPrice;

    @Column(name = "QUANTITY")
    private int quantity;

    @ManyToMany(cascade = CascadeType.MERGE, mappedBy = "products")
    private List<Order> orders = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.MERGE, mappedBy = "products")
    private List<Cart> carts = new ArrayList<>();

    public Product(final String productName, final double productPrice, final int quantity) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }

    public Product(Long productId, String productName, double productPrice, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }
}
