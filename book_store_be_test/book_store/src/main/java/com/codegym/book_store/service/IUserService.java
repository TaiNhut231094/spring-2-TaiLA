package com.codegym.book_store.service;

import com.codegym.book_store.model.User;

import java.util.Optional;

public interface IUserService {
    Optional<User> findByUsername(String name);

    Boolean existsByUsername (String name);

    Boolean existsByEmail (String email);

    User save (User user);
}
