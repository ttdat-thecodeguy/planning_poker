package com.springboot.planning_poker.model.business.impl;

import com.springboot.planning_poker.model.business.IRole;
import com.springboot.planning_poker.model.definition.StatusCode;
import com.springboot.planning_poker.model.enity.Role;
import com.springboot.planning_poker.model.responsitory.RoleRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor @Slf4j @Component @Transactional
public class RoleService implements IRole {
    private final RoleRepo roleRepo;
    @Override
    public Role addRole(Role role) throws Exception {
        if(roleRepo.findByName(role.getName()).orElse(null) != null){
            throw new Exception(StatusCode.ROLE_EXISTS);
        }
        return roleRepo.save(role);
    }
}
