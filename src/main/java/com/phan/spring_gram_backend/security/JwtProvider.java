package com.phan.spring_gram_backend.security;

import com.phan.spring_gram_backend.model.User;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.phan.spring_gram_backend.security.SecurityConstants.EXPIRATION_TIME;
import static com.phan.spring_gram_backend.security.SecurityConstants.JWT_SECRET;

@Component
public class JwtProvider {

    public String generateToken(Authentication authentication) {

        // Extracts user from spring security principal
        User user = (User) authentication.getPrincipal();

        // Creating Date object and creating expiration date object
        Date now = new Date();
        Date expirationDate = new Date((now.getTime() + EXPIRATION_TIME));

        // Getting user id from user object
        String userId = Long.toString(user.getId());

        // Creating claims map
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", (Long.toString(user.getId())));
        claims.put("username", user.getUsername());
        claims.put("fullName", user.getFullName());

        // Building JWT token
        return Jwts.builder()
                .setSubject(userId)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    // Checks digital signature of JWT to validate and verify authenticity
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            System.out.println("Invalid JWT Signature");
        } catch (MalformedJwtException ex) {
            System.out.println("Invalid JWT Token");
        } catch (ExpiredJwtException ex) {
            System.out.println("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            System.out.println("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            System.out.println("JWT claims string is empty");
        }
        return false;
    }

    //Get user Id from token
    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
        String id = (String) claims.get("id");

        return Long.parseLong(id);
    }

}
