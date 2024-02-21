package com.project.sportsManagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.security.core.GrantedAuthority;
@Entity
@Table(name = "role")
public class Role implements GrantedAuthority {
    @Id
    @Column(name = "role_id")
    private int roleId;
    @Column(name = "authority")
    private String authority;

    public Role() {
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public Role(String authority) {
        this.authority = authority;
    }

    public Role(int roleId, String authority) {
        this.roleId = roleId;
        this.authority = authority;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
