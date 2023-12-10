package com.jwt.security.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Component
public class JwtUtil {
    private static  final String SECRET_KEY = "TEST_12_APPLICATION" ;
    private static final long TOKEN_VALIDITY = 36000;

    public String getUserNameFromToken(String jwtToken) {
        return getClaimsFromToken(jwtToken, Claims::getSubject);
    }

    private <T> T getClaimsFromToken(String token , Function<Claims, T> claimsResolver) {
       final Claims claims = getAllClaimsFromToken(token);
       return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }


    public boolean validateToken(String token, UserDetails userDetails) {
        String userName = getUserNameFromToken(token);
//        check my username equals to getUserName
        return ( userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
       final Date expirationDate =  getExpirationDateFromToken(token);
       return expirationDate.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimsFromToken(token, Claims::getExpiration);
    }

    public String generateToken(UserDetails userDetails) {
        // as of now we are not storing any claims
        java.util.Map<String, Object> claims = new HashMap<>() ;
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }
}
