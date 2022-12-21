package com.newgo.activity.supermarketapp.presentation.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductDTO extends IdentifiedDTO {

    private String name;
    private String description;
    private Boolean active;
    private BigDecimal price;
    private String photo;
}
