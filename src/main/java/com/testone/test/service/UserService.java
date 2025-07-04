package com.testone.test.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testone.test.exception.ResourceNotFound;
import com.testone.test.model.Post;
import com.testone.test.model.User_;
import com.testone.test.repository.PostRepo;
import com.testone.test.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepo userRepo;
//    private final PostRepo postRepo;
    @Override
    public List<User_> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User_ addUser(User_ user) {
        return userRepo.save(user);
    }

    @Override
    public User_ updateUser(User_ user) {
        try {
            User_ has_user=userRepo.findById(user.getUser_id())
                    .stream().findFirst().orElse(null);
            if(has_user !=null){
                return has_user;
            }
            return null;
        } catch (ResourceNotFound e) {
            throw new ResourceNotFound("Not Found: ");
        }

    }

    @Override
    public User_ getUser(Long userId) {
        return userRepo.findById(userId).orElseThrow(()->new ResourceNotFound("not found"));

    }

    @Override
    public String deleteUser(Long userId) throws ResourceNotFound  {
        return userRepo.findById(userId)
                .map(us-> {
                    userRepo.delete(us);
                    return "success";
                })
                .orElse(null);
    }

    @Override
    public List<Post> getUserPosts(User_ user) {
        User_ _user=getUser(user.getUser_id());
        if(_user!=null){
            return _user.getPosts();
        }
        return new ArrayList<>();
    };

    public String getUserAsJson(User_ user) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(user); // Convert ArrayList to JSON string
    }

    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {

            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

                System.out.println("UserDetailsService:username"+ username);

                return userRepo.findByEmail(username)
                        .orElseThrow(()->new UsernameNotFoundException("not found"));
            }
        };
    };

    public User_ save(User_ newUser){
        return userRepo.save(newUser);
    }


}
