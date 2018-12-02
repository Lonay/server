package com.dawid.sternik.server.controllers;


import com.dawid.sternik.server.entity.LoginForm;
import com.dawid.sternik.server.entity.User;
import com.dawid.sternik.server.entity.UserRepository;
import com.dawid.sternik.server.service.UserAuthenticationService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/public/users")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
final class PublicUsersController {
    @NonNull
    UserAuthenticationService authentication;
    @NonNull
    UserRepository users;

    @PostMapping("/register")
    String register(@RequestBody LoginForm loginForm) {
        users
                .save(
                        User
                                .builder()
                                .uuid(loginForm.getUsername())
                                .username(loginForm.getUsername())
                                .password(loginForm.getPassword())
                                .build()
                );

        return login(loginForm);
    }

    @PostMapping("/login")
    String login(@RequestBody LoginForm loginForm) {
        return authentication
                .login(loginForm.getUsername(), loginForm.getPassword())
                .orElseThrow(() -> new RuntimeException("invalid login and/or password"));
    }
}