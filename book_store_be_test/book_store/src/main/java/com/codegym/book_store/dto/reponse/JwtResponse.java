package com.codegym.book_store.dto.reponse;

import com.codegym.book_store.model.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private Long id;
    private String token;
    private String type = "Bearer";
    private String name;
    private String username;
    private String email;
    private String avatar;
    private String phoneNumber;
    private Address address;
    private Collection<? extends GrantedAuthority> roles;

    public JwtResponse(String token, Long id, String name, String username, String email, String avatar, String phoneNumber, Address address, Collection<? extends GrantedAuthority> roles) {
        this.token = token;
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.avatar = avatar;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.roles = roles;
    }
}
