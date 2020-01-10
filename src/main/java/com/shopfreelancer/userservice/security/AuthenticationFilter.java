package com.shopfreelancer.userservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopfreelancer.userservice.service.UserService;
import com.shopfreelancer.userservice.shared.dto.UserDto;
import com.shopfreelancer.userservice.ui.model.request.UserLoginRequestModel;

import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import static java.lang.System.currentTimeMillis;


public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    private Environment environment;
    private UserService userService;

    public AuthenticationFilter(UserService usersService,
                                Environment environment,
                                AuthenticationManager authenticationManager) {
        this.userService = usersService;
        this.environment = environment;

        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {

            String contentType = request.getHeader("Accept");

            UserLoginRequestModel creds = new ObjectMapper()
                    .readValue(request.getInputStream(), UserLoginRequestModel.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getEmail(),
                            creds.getPassword(),
                            new ArrayList<>())
            );

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String userName = ((User) authResult.getPrincipal()).getUsername(); // this is email
        UserDto userDto = userService.getUser(userName);

        String token = Jwts.builder()
                .setSubject(userName)
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(environment.getProperty("app.token.expiration.time"))))
                .signWith(SignatureAlgorithm.HS512, environment.getProperty("app.token.secret") )
                .compact();

        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("UserID", userDto.getUserId());

    }


}
