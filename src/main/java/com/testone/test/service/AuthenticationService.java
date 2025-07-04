package com.testone.test.service;
import com.testone.test.dto.JwtAuthenticationresponse;
import com.testone.test.dto.SignUpRequest;
import com.testone.test.dto.SigninRequest;
import com.testone.test.model.Role;
import com.testone.test.model.User_;
import com.testone.test.repository.UserRepo;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.http.HttpHeaders;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final UserRepo userRepo;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final HttpServletResponse response;


    public JwtAuthenticationresponse signup(SignUpRequest request){
        var user= User_
                .builder()
                .firstName(request.getFirstName())
                .lastname(request.getLastName())
                .email(request.getEmail())
                .posts(List.of())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER).build();
        user=userService.save(user);
        String jwt =jwtService.generateToken2(user);
        return JwtAuthenticationresponse.builder()
                .token(jwt)
                .email(user.getEmail())
                .firstName((user.getFirstName()))
                .lastName(user.getLastname())
                .id(user.getUser_id())
                .build();
    };


    public JwtAuthenticationresponse signin(SigninRequest request){


        var user= userRepo.findByEmail(request.getEmail()).orElseThrow(()->new IllegalArgumentException(" wrong email"));

        UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword(),user.getAuthorities());
        log.debug("3.)=>BEFORE AUTHENTICATING USER AUTHTOKEN-{}",authToken.toString());
        authenticationManager.authenticate(authToken); //User is not _User UserDetails
        var jwt=jwtService.generateToken2(user);
        InsertToken(jwt);


        return JwtAuthenticationresponse.builder()
                .token(jwt)
                .email(user.getEmail())
                .firstName((user.getFirstName()))
                .lastName(user.getLastname())
                .id(user.getUser_id())
                .build();


    }

    public void InsertToken(String token){
        String bearer="Bearer " + token;
        response.setHeader("Authorization",bearer);

    }



}
