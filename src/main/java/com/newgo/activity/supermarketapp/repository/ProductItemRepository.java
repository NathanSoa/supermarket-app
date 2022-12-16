package com.newgo.activity.supermarketapp.repository;

import com.newgo.activity.supermarketapp.domain.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductItemRepository extends JpaRepository<ProductItem, Long> {
}
