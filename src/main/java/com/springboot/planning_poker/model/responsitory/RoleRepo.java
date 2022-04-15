package com.springboot.planning_poker.model.responsitory;

import com.springboot.planning_poker.model.enity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Integer> {
}
