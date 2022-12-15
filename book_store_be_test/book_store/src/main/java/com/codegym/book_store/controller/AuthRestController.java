package com.codegym.book_store.controller;

import com.codegym.book_store.dto.reponse.GooglePojo;
import com.codegym.book_store.dto.reponse.JwtResponse;
import com.codegym.book_store.dto.reponse.ResponseMessage;
import com.codegym.book_store.dto.request.SignInForm;
import com.codegym.book_store.dto.request.SignUpForm;
import com.codegym.book_store.model.Role;
import com.codegym.book_store.model.RoleName;
import com.codegym.book_store.model.User;
import com.codegym.book_store.security.jwt.GoogleUtils;
import com.codegym.book_store.security.jwt.JwtProvider;
import com.codegym.book_store.security.userprincal.UserDetailService;
import com.codegym.book_store.security.userprincal.UserPrinciple;
import com.codegym.book_store.service.impl.RoleService;
import com.codegym.book_store.service.impl.UserService;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
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
    private UserDetailService userDetailsService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private GoogleUtils googleUtils;


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
        return ResponseEntity.ok(new JwtResponse(token, userPrinciple.getId(), userPrinciple.getName(), userPrinciple.getUsername(), userPrinciple.getEmail(), userPrinciple.getAvatar(), userPrinciple.getPhoneNumber(), userPrinciple.getAddress(), userPrinciple.getAuthorities()));
    }

    @PostMapping("/login-google")
    public ResponseEntity<?> loginGoogle(HttpServletRequest request) throws ClientProtocolException, IOException {
        String code = request.getParameter("token");

//        if (code == null || code.isEmpty()) {
//            return "redirect:/login?google=error";
//        }
        String accessToken = googleUtils.getToken(code);

        GooglePojo googlePojo = googleUtils.getUserInfo(accessToken);
        UserPrinciple userPrinciple = (UserPrinciple) userDetailsService.loadUserGooglePojo(googlePojo);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userPrinciple.getEmail(), null,
                userPrinciple.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok(new JwtResponse(accessToken, userPrinciple.getId(), userPrinciple.getName(), userPrinciple.getUsername(), userPrinciple.getEmail(), userPrinciple.getAvatar(), userPrinciple.getPhoneNumber(), userPrinciple.getAddress(), userPrinciple.getAuthorities()));
    }
}
