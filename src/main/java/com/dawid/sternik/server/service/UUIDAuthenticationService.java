package com.dawid.sternik.server.service;

import com.dawid.sternik.server.entity.User;
import com.dawid.sternik.server.entity.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Service
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class UUIDAuthenticationService implements UserAuthenticationService {
    @NonNull
    UserRepository users;

    @Override
    public Optional<String> login(final String username, final String password) {
        final String uuid = UUID.randomUUID().toString();
        final User user = User
                .builder()
                .uuid(uuid)
                .username(username)
                .password(password)
                .build();

        users.save(user);
        return Optional.of(uuid);
    }

    @Override
    public Optional<User> findByToken(final String token) {

        return users.findByUuid(token);
    }

    @Override
    public void logout(final User user) {

    }
}