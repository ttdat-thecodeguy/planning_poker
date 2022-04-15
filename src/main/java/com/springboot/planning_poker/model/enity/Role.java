package com.springboot.planning_poker.model.enity;

import com.springboot.planning_poker.model.definition.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity @NoArgsConstructor
@AllArgsConstructor
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    public Role(RoleEnum roleEnum){
        RoleEnum r = null;
        switch (roleEnum){
            case ROLE_USER:
                r = RoleEnum.ROLE_USER;
                break;
            case ROLE_ADMIN:
                r = RoleEnum.ROLE_ADMIN;
                break;
        }
        this.id = r.getId();
        this.name = r.getName();
    }
}
