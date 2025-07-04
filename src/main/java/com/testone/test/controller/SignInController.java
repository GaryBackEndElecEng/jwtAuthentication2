package com.testone.test.controller;

import com.testone.test.dto.SignUpRequest;
import com.testone.test.dto.SigninRequest;
import com.testone.test.exception.ResourceNotFound;
import com.testone.test.model.Signin;
import com.testone.test.model.UserToken;
import com.testone.test.model.User_;
import com.testone.test.response.ApiResponse;
import com.testone.test.service.AuthenticationService;
import com.testone.test.service.IUserDtoService;
import com.testone.test.service.SigninService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class SignInController {

    private final SigninService signinService;
    private final AuthenticationService authenticationService;



    @PostMapping("/signup")
    //"message": "Cannot invoke \"java.util.List.toArray()\" because \"this.posts\" is null"=>> FIX
    public Object signup(@RequestBody SignUpRequest request){
        try {
            return authenticationService.signup(request);
        } catch (Exception e) {
            return ResponseEntity.ok().body(new ApiResponse(null,e.getMessage()));
        }
    }
    @PostMapping("/signin")
    public Object signin(@RequestBody SigninRequest request){

        try {
            return authenticationService.signin(request);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }



}
