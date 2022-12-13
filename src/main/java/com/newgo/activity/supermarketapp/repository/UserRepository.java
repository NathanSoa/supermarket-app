package com.newgo.activity.supermarketapp.repository;

import com.newgo.activity.supermarketapp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
