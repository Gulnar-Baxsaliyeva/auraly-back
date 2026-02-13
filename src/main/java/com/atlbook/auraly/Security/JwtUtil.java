package com.atlbook.auraly.Security;

import com.atlbook.auraly.dao.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secret_key}")
    private byte[] SECRET_KEY;
    @Value("${jwt.expired_date}")
    private Long EXPIRED_DATE;
    public String generatedToken (UserEntity user){
        return Jwts.builder()
                .signWith( secretKey())
                .setSubject(user.getEmail())
                .claim("role", user.getRole())
                .setExpiration(
                        new Date(System.currentTimeMillis()+EXPIRED_DATE)
                )
                .compact();
    }

    public Claims decodeToken(String token){
        return Jwts.parserBuilder().setSigningKey(secretKey()).build().parseClaimsJws(token).getBody();
    }


    public String getUsername(String token){
        try{
            return decodeToken(token).getSubject();
        }catch(Exception e ){
            throw new RuntimeException("Token expired olub");
        }
    }

    public boolean isValid(String token){
        return decodeToken(token).getExpiration().after(new Date());
    }

    private Key secretKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY);
    }
}

