package com.kodilla.ecommercee.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Getter
@Table(name = "GroupTable")
public class Group {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "GROUP_ID")
    private Long id;

    @Column(name = "GROUP_NAME")
    private String groupName;

    @OneToMany(
            targetEntity = Product.class,
            mappedBy = "group",
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST
    )
    private List<Product> products = new ArrayList<>();

    public Group(String groupName) {
        this.groupName = groupName;
    }

    public Group(String groupName, List<Product> products) {
        this.groupName = groupName;
        this.products = products;
    }
}
