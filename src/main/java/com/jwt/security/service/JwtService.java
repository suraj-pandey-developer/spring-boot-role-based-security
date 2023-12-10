package com.jwt.security.service;

import com.jwt.security.model.User;
import com.jwt.security.model.payload.AuthRequest;
import com.jwt.security.model.payload.AuthResponse;
import com.jwt.security.repository.UserDao;
import com.jwt.security.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class JwtService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private JwtUtil jwtUtil;

    public AuthResponse createJwtToken(AuthRequest authRequest) throws Exception{
        String userName = authRequest.getUsername();
        String userPassword = authRequest.getUserPassword();
        authenticate(userName, userPassword);
        final UserDetails userDetails = loadUserByUsername(userName);
        String newGenerateJwttoken = jwtUtil.generateToken(userDetails);
        User user = userDao.findById(userName).get();
        return new AuthResponse(user, newGenerateJwttoken);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findById(username).get();
        if(user != null) {
            // here we have to use which provided by spring security
            // it takes three parameters username, userpassword and authorities
            // for authorities we are creating a function
            return new org.springframework.security.core.userdetails.User(
                    user.getUserName(),
                    user.getUserPassword(),
                    getAuthorities(user));
        } else {
            throw new UsernameNotFoundException("user name is not valid");
        }
    }

    // authorities function
    private Set getAuthorities(User user) {
        Set authorities = new HashSet();
        // for authority we have to follow some convention capital "ROLE_"
        user.getRole().forEach( role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRolename()));
        });

        return authorities;
    }

    private void authenticate(String username, String userPassword) throws Exception {

        /// this authentication manager return two exception
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, userPassword));
        } catch (DisabledException e) {
            throw new Exception("User is disabled") ;
        } catch (BadCredentialsException e) {
            throw new Exception("Bad creadentidals from user");
        }
    }


}
