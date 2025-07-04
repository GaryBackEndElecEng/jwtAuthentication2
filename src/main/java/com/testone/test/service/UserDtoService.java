package com.testone.test.service;

import com.testone.test.dto.UserDto;
import com.testone.test.dto.UserDtoNoPosts;
import com.testone.test.model.User_;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDtoService implements IUserDtoService {

    @Override
    public List<UserDto> getAllUsers(List<User_> users) {
        return this.convertUsers(users);
    };


    @Override
    public UserDto updateUser(User_ user) {
        return this.convertUser(user);
    };

    @Override
    public UserDto getUser(User_ user) {
        return this.convertUser(user);
    };
    @Override
    public List<UserDto> convertUsers(List<User_> users){
        List<UserDto> userDtos=new ArrayList<>();
        users.forEach(user->{
                    userDtos.add(this.convertUser(user));
                });
        return userDtos;

    };
    @Override
    public List<UserDtoNoPosts> convertNoPostsUsers(List<User_> users){
        List<UserDtoNoPosts> userDtoNoPosts=new ArrayList<>();
        users.forEach(user->{
                    userDtoNoPosts.add(this.convertUserNoPosts(user));
                });
        return userDtoNoPosts;
    };

    @Override
    public UserDto convertUser(User_ user){
        UserDto userDto=new UserDto();
        userDto.setUser_id(user.getUser_id());
        userDto.setEmail(user.getEmail());
        userDto.setLastname(user.getLastname());
        userDto.setUsername(user.getUsername());
        userDto.setImg(user.getImg());
        userDto.setPosts(user.getPosts());
        userDto.setIsLoggedIn(false);
        userDto.setNum_posts(user.getNum_posts());
        userDto.onCreate();
        return userDto;
    };
    public UserDtoNoPosts convertUserNoPosts(User_ user){
        UserDtoNoPosts userDtoNoPost=new UserDtoNoPosts();
        userDtoNoPost.setUser_id(user.getUser_id());
        userDtoNoPost.setEmail(user.getEmail());
        userDtoNoPost.setLastname(user.getLastname());
        userDtoNoPost.setUsername(user.getUsername());
        userDtoNoPost.setImg(user.getImg());
        userDtoNoPost.setIsLoggedIn(false);
        userDtoNoPost.onCreate(user.getPosts());
        return userDtoNoPost;
    };
}
