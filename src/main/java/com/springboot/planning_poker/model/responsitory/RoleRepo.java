package com.springboot.planning_poker.model.responsitory;

import com.springboot.planning_poker.model.enity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}
