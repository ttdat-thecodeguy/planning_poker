package com.springboot.planning_poker.model.definition;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public enum RoleEnum {
    ROLE_USER(1, "ROLE_USER"), ROLE_ADMIN(2, "ROLE_ADMIN");
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
