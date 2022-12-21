package com.newgo.activity.supermarketapp.presentation.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;


@Getter
@Setter
public class ProductItemRequest {

    @NotNull
    private String name;

    @NotNull
    private Integer quantity;
}
