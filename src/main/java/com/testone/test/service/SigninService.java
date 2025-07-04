package com.testone.test.service;

import com.testone.test.model.Signin;
import com.testone.test.model.UserToken;
import com.testone.test.model.User_;
import com.testone.test.repository.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Transactional
@Service
@RequiredArgsConstructor
public class SigninService implements ISigninService {
    private final UserRepo userRepo;
    private final JwtService jwtService;

    private final UserToken userToken = new UserToken();
    @Override
    public Boolean verfiyPassword(Signin signin) {
        User_ user= this.getUser(signin);
        return this.checkPassword(user,signin.getPassword());
    }
    @Override
    public User_ getUser(Signin signin){
        String email=signin.getEmail();
//        User_ getUser= userRepo.findByEmail(email);
//        System.out.println("this: " + getUser);
        return userRepo.findAll().stream().filter(user->user.getEmail().equals(email)).findFirst().orElse(null);
    };

    public Boolean checkPassword(User_ user,String pswd){
        if(user==null) return false;
        return user.getPassword().equals(pswd);
    }

    @Override
    public UserToken addToken(User_ user){
        String token=jwtService.generateToken2(user);
        userToken.setUser_id(user.getUser_id());
        userToken.setEmail(user.getEmail());
        userToken.setImg(user.getImg());
        userToken.setLastname(user.getLastname());
        userToken.setPosts(user.getPosts());
        userToken.setRegistered(true);
        userToken.setToken(token);
        return userToken;
    }
}
