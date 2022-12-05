package com.codegym.book_store.security.jwt;

import com.codegym.book_store.security.userprincal.UserPrinciple;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
    private final String SECRET = "tainhut";
    private int jwtExpiration = 60 * 60 * 24;

    public String createToken(Authentication authentication) {
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        return Jwts.builder().setSubject(userPrinciple.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpiration * 1000))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature -> Message: {}", e);
        } catch (MalformedJwtException e) {
            logger.error("Invalid format Token -> Message: {}", e);
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT Token -> Message: {}", e);
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT Token -> Message: {}", e);
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty --> Message: {}", e);
        }
        return false;
    }

    public String getUserNameFromToken(String token) {
        String userName = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().getSubject();
        return userName;
    }
}