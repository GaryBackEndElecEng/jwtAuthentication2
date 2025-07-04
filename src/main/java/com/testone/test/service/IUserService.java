package com.testone.test.service;

import com.testone.test.exception.ResourceNotFound;
import com.testone.test.model.Post;
import com.testone.test.model.User_;

import java.util.List;

public interface IUserService {
    List<User_> getAllUsers();
    User_ addUser(User_ user);
    User_ updateUser(User_ user);
    User_ getUser(Long userId);
    String deleteUser(Long userId);
    List<Post> getUserPosts(User_ user);
}
