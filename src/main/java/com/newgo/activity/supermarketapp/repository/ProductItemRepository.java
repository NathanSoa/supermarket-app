package com.newgo.activity.supermarketapp.repository;

import com.newgo.activity.supermarketapp.domain.ProductItem;
import com.newgo.activity.supermarketapp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductItemRepository extends JpaRepository<ProductItem, Long> {

    List<ProductItem> findAllByUser(User user);
    void deleteAllByUser(User user);
}
