package com.springboot.planning_poker.model.responsitory;

import com.springboot.planning_poker.model.enity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findUserByEmail(String email);
}
