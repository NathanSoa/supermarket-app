package com.newgo.activity.supermarketapp.presentation.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class ProductItemDTO extends IdentifiedDTO {

    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private String photo;
}
