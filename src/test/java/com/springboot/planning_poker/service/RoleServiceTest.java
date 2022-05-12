package com.springboot.planning_poker.service;

import com.springboot.planning_poker.model.business.IRole;
import com.springboot.planning_poker.model.definition.RoleEnum;
import com.springboot.planning_poker.model.definition.StatusCode;
import com.springboot.planning_poker.model.enity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class RoleServiceTest {
    @Autowired
    IRole roleBus;
    @Test
    public void whenAddRoleValid_returnThisRole() throws Exception {
        Role role
                = new Role(null, "ROLE NEW");
        assertThat(roleBus.addRole(role).getName()).isEqualTo(role.getName());
    }

    @Test
    public void whenAddRoleExists_throwExceptionHasMessage(){
        Role roleUser = new Role(RoleEnum.ROLE_USER);
        assertThatThrownBy(() -> roleBus.addRole(roleUser)).isInstanceOf(Exception.class).hasMessage(StatusCode.ROLE_EXISTS);
    }
}
