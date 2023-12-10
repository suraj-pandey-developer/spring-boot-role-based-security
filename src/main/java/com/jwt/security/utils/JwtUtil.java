package com.jwt.security.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {
    private static  final String SECRET_KEY = "TEST_12_APPLICATION" ;

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
}
