package com.newgo.activity.supermarketapp.data.repository;

import com.newgo.activity.supermarketapp.data.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
