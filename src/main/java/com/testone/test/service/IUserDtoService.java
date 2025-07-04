package com.testone.test.service;

import com.testone.test.dto.UserDto;
import com.testone.test.dto.UserDtoNoPosts;
import com.testone.test.model.User_;

import java.util.List;

public interface IUserDtoService {

    List<UserDto> getAllUsers(List<User_> users);
    UserDto updateUser(User_ user);
    UserDto getUser(User_ user);

    List<UserDto> convertUsers(List<User_> users);

    List<UserDtoNoPosts> convertNoPostsUsers(List<User_> users);

    UserDto convertUser(User_ user);
}
