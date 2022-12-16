package com.newgo.activity.supermarketapp.repository;

import com.newgo.activity.supermarketapp.domain.Product;

import com.newgo.activity.supermarketapp.repository.product.ProductRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryQuery {

    Optional<Product> findByName(String name);
}
