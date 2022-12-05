package com.codegym.book_store.service.impl;

import com.codegym.book_store.model.Role;
import com.codegym.book_store.model.RoleName;
import com.codegym.book_store.repository.IRoleRepository;
import com.codegym.book_store.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class RoleService implements IRoleService {

    @Autowired
    IRoleRepository roleRepository;

    @Override
    public Optional<Role> findByName(RoleName name) {
        return roleRepository.findByName(name);
    }
}
