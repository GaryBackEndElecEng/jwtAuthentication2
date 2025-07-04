package com.testone.test.service;

import com.testone.test.model.User_;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static io.jsonwebtoken.Jwts.builder;

@Service
@AllArgsConstructor
public class JwtService {

    private final String jwtSecretKey="6IqjDraQ/OOVxvEVcydax6mGYlEeM+veiwi6qjyj3X9VHl9lFUCGplJT74PAMFwb";
    private final Integer jwtExpirationms=60 *60 * 24*60;

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }


    public boolean isTokenValid(String token, UserDetails userdetails){
        final String userName=extractUsername(token);
        return (userName.equals(userdetails.getUsername())) && !isTokenExpired(token);
    }


    public String generateToken(Map<String,Object> claims,UserDetails userDetails) {

        return builder()
                .claims()
                .add(claims)
                .subject(userDetails.getUsername().trim())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationms))
                .and()
                .signWith(getKey(jwtSecretKey.trim()))
                .compact();
    };

    private <T> T extractClaim(String token, Function<Claims,T> claimResolver){
        final Claims claims=extractAllClaims(token);
        System.out.println("CLAIMS: "+ claims);
        return claimResolver.apply(claims);
    }
    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getKey(jwtSecretKey))
                .build().parseSignedClaims(token).getPayload();
    }


    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token){
        return extractClaim(token,Claims::getExpiration);
    }
    private String extractIssuer(String token){
        return extractClaim(token,Claims::getIssuer);
    }

    private SecretKey getKey(String secretKey) {
        byte[] key= Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(key);
    };

    public String generateToken2(UserDetails userDetails) {

        Map<String,Object> claims= new HashMap<>();
        return builder()
                .claims()
                .add(claims)
                .subject(userDetails.getUsername().trim())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationms))
                .and()
                .signWith(getKey(jwtSecretKey.trim()))
                .compact();
    };

}