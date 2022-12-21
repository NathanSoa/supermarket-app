package com.newgo.activity.supermarketapp.presentation.dtos;

import javax.validation.constraints.NotNull;

public class ProductItemRequest {

    @NotNull
    private String name;

    @NotNull
    private Integer quantity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
