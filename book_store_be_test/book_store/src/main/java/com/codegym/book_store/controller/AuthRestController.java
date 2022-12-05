package com.codegym.book_store.controller;

import com.codegym.book_store.dto.reponse.JwtResponse;
import com.codegym.book_store.dto.reponse.ResponseMessage;
import com.codegym.book_store.dto.request.SignInForm;
import com.codegym.book_store.dto.request.SignUpForm;
import com.codegym.book_store.model.Role;
import com.codegym.book_store.model.RoleName;
import com.codegym.book_store.model.User;
import com.codegym.book_store.security.jwt.JwtProvider;
import com.codegym.book_store.security.userprincal.UserPrinciple;
import com.codegym.book_store.service.impl.RoleService;
import com.codegym.book_store.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RequestMapping("api/public/auth")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/sign-up")
    public ResponseEntity<?> register(@Valid @RequestBody SignUpForm signUpForm) {
        if (userService.existsByUsername(signUpForm.getUsername())) {
            return new ResponseEntity<>(new ResponseMessage("The username existed! Please try again!"), HttpStatus.OK);
        }
        if (userService.existsByEmail(signUpForm.getEmail())) {
            return new ResponseEntity<>(new ResponseMessage("The email existed! Please try again!"), HttpStatus.OK);
        }
        User user = new User(signUpForm.getName(), signUpForm.getUsername(), signUpForm.getEmail(), passwordEncoder.encode(signUpForm.getPassword()));
        Set<Role> roles = new HashSet<>();
        Role userRole = roleService.findByName(RoleName.ROLE_USER).orElseThrow(() ->
                new RuntimeException("Role not found"));
        roles.add(userRole);
        user.setRoles(roles);
        userService.save(user);
        return new ResponseEntity<>(new ResponseMessage("Create user success!"), HttpStatus.OK);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> login(@RequestBody Optional<SignInForm> signInForm) {
        if (!signInForm.isPresent()) {
            return new ResponseEntity<>("Không được để trống tài khoản, mật khẩu", HttpStatus.BAD_REQUEST);
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInForm.get().getUsername(), signInForm.get().getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.createToken(authentication);
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(token, userPrinciple.getName(), userPrinciple.getUsername(), userPrinciple.getAvatar(), userPrinciple.getPhoneNumber(), userPrinciple.getAddress(), userPrinciple.getAuthorities()));
    }
}
