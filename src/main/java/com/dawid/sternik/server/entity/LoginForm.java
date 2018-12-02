package com.dawid.sternik.server.entity;

import lombok.Data;

import javax.persistence.Entity;

@Data
public class LoginForm {
    private String username;
    private String password;
}
