package com.codegym.book_store.service;

import com.codegym.book_store.model.Role;
import com.codegym.book_store.model.RoleName;

import java.util.Optional;

public interface IRoleService {
    Optional<Role> findByName(RoleName name);
}
