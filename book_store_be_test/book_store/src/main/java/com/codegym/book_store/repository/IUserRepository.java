package com.codegym.book_store.repository;

import com.codegym.book_store.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String name);

    Boolean existsByUsername (String name);

    Boolean existsByEmail (String email);
}
