package com.newgo.activity.supermarketapp.repository;

import com.newgo.activity.supermarketapp.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
