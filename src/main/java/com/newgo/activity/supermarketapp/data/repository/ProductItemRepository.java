package com.newgo.activity.supermarketapp.data.repository;

import com.newgo.activity.supermarketapp.data.entities.Product;
import com.newgo.activity.supermarketapp.data.entities.ProductItem;
import com.newgo.activity.supermarketapp.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductItemRepository extends JpaRepository<ProductItem, Long> {

    List<ProductItem> findAllByUser(User user);
    Optional<ProductItem> findByProductAndUser(Product product, User user);
    void deleteAllByUser(User user);
    void deleteByProductAndUser(Product product, User user);
}
