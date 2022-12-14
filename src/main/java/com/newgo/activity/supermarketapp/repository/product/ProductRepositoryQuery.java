package com.newgo.activity.supermarketapp.repository.product;

import com.newgo.activity.supermarketapp.domain.Product;
import com.newgo.activity.supermarketapp.repository.filter.ProductFilter;

import java.util.Set;

public interface ProductRepositoryQuery {

    Set<Product> findAllFiltered(ProductFilter productFilter);
}
