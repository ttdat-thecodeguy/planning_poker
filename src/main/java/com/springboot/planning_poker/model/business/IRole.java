package com.springboot.planning_poker.model.business;

import com.springboot.planning_poker.model.enity.Role;

public interface IRole {

    Role addRole(Role role) throws Exception;
    Role findRoleById(Integer id);
}
