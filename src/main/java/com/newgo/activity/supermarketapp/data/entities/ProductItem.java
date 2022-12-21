package com.newgo.activity.supermarketapp.data.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "product_item")
public class ProductItem extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Integer quantity;
}
