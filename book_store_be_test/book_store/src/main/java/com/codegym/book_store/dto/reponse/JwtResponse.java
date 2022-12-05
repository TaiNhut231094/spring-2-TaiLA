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
    private String avatar;
    private String phoneNumber;
    private Address address;
    private Collection<? extends GrantedAuthority> roles;

    public JwtResponse(String token, String name, String username, String avatar, String phoneNumber, Address address, Collection<? extends GrantedAuthority> roles) {
        this.token = token;
        this.name = name;
        this.username = username;
        this.avatar = avatar;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.roles = roles;
    }
}
