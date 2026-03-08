package com.example.demo.Dto;

import com.example.demo.Entity.Role;

public class LoginResponseDto {

    private Integer id;
    private String name;
    private String email;
    private Role role;

    public LoginResponseDto(Integer id, String name, String email, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    // getters only if response is read-only
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }
}
