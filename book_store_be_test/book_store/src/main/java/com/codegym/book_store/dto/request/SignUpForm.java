package com.codegym.book_store.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpForm {
    private Long id;
    private String name;
    private String username;
    private String email;
    private String password;
    private String phoneNumber;
    private Set<String> roles;
}
