package com.jwt.security.config;


import com.jwt.security.service.JwtService;
import com.jwt.security.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;
    // custom jwt service which extend customUserDetails
    @Autowired
    private JwtService jwtService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
       final String header = request.getHeader("Authorization");
       String jwtToken = null ;
       String userName = null;
       if(header != null && header.startsWith("Bearer ")) {
           jwtToken = header.substring(7);
           try {
                userName = jwtUtil.getUserNameFromToken(jwtToken);
           } catch (IllegalArgumentException e) {
               System.out.println("unable to get JWT token for User");
           }
       } else {
           System.out.println(" jwt token does not start with bearer");
       }

       if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = jwtService.loadUserByUsername(userName);
            if(jwtUtil.validateToken(jwtToken, userDetails)) {
               UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =  new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());

               usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
               SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
       }
       // just user filter chaing
        // responsible for try to retrive the authorization the request header and it will try to authorize the user request

        filterChain.doFilter(request,response);
    }
}
