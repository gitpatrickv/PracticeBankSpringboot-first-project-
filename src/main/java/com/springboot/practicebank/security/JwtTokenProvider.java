package com.springboot.practicebank.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${application.security.jwt.secret-key}")
    private String jwtSecretKey;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    //@Value("${application.security.jwt.refresh-token.expiration}")
    //private long refreshTokenExpiration;

    private SecretKey key(){
        byte[] bytes = Decoders.BASE64.decode(jwtSecretKey);
        return Keys.hmacShaKeyFor(bytes);
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        // HttpServletRequest request provides methods to access request headers, parameters, and other details, allowing you to extract the token.

        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpiration);

        return Jwts.builder()
                .subject(username)
                .issuedAt(currentDate)
                .expiration(expireDate)
                .signWith(key())
                .compact();
    }
/*
    public String generateRefreshToken(Map<String, Object> extraClaims, Authentication authentication){
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + refreshTokenExpiration);

        return Jwts.builder()
                .claims(extraClaims)
                .subject(username)
                .issuedAt(currentDate)
                .expiration(expireDate)
                .signWith(key())
                .compact();
    }
 */
    public String getUsername(String token){

        Claims claims = Jwts.parser()
                .verifyWith(key())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();

    }

    public boolean validateToken(String token) {
        try{
            Jwts.parser()
                    .verifyWith(key())
                    .build()
                    .parse(token);
            return true;
        }
        catch (ExpiredJwtException | IllegalArgumentException | SecurityException | MalformedJwtException e){
            throw new RuntimeException(e);
        }
    }
}
