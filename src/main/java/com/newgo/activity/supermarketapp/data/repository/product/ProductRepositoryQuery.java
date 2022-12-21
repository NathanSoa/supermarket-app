package com.newgo.activity.supermarketapp.data.repository.product;

import com.newgo.activity.supermarketapp.data.entities.Product;
import com.newgo.activity.supermarketapp.data.repository.filter.ProductFilter;

import java.util.Set;

public interface ProductRepositoryQuery {

    Set<Product> findAllFiltered(ProductFilter productFilter);
}
