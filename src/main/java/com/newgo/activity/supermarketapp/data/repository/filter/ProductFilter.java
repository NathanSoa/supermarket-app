package com.newgo.activity.supermarketapp.data.repository.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductFilter {

    private String name;
    private String description;
    private Boolean active;
}
