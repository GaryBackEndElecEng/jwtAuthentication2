package com.testone.test.controller;

import com.testone.test.dto.UserDto;
import com.testone.test.dto.UserDtoNoPosts;
import com.testone.test.exception.ResourceNotFound;
import com.testone.test.model.Post;
import com.testone.test.model.User_;
import com.testone.test.response.ApiResponse;
import com.testone.test.service.IUserDtoService;
import com.testone.test.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {

    private final IUserService userService;
    private final IUserDtoService userDtoService;

    @GetMapping("/all")
    ResponseEntity<ApiResponse> getAllUsers(){
        try {
            List<User_> users=userService.getAllUsers();
            List<UserDtoNoPosts> userDtoNoPosts=userDtoService.convertNoPostsUsers(users);
            return ResponseEntity.ok().body(new ApiResponse(userDtoNoPosts,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(null,e.getMessage()));
        }
    };

    @PreAuthorize("hasRole('USER','ADMIN)")
    @PostMapping("/post")
    ResponseEntity<ApiResponse> addUser(@RequestBody  User_ user){
        try {
            User_ _user=userService.addUser(user);
            UserDto userDto=userDtoService.convertUser(_user);
            return ResponseEntity.ok().body(new ApiResponse(userDto,"success"));
        } catch (ResourceNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(null,e.getMessage()));
        }
    };

    @PutMapping("/put")
    ResponseEntity<ApiResponse> updateUser(@RequestBody  User_ user){
        try {
            User_ _user=userService.updateUser(user);
            UserDto userDto=userDtoService.updateUser(_user);
            return ResponseEntity.ok().body(new ApiResponse(userDto,"success"));
        } catch (ResourceNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(null,e.getMessage()));
        }
    };

    @GetMapping("/get/{userId}")
    ResponseEntity<ApiResponse> getUser(@PathVariable  Long userId){
        try {
            User_ _user=userService.getUser(userId);
            UserDto userDto=userDtoService.getUser(_user);
            return ResponseEntity.ok().body(new ApiResponse(userDto,"success"));
        } catch (ResourceNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(null,e.getMessage()));
        }
    };
    @GetMapping("/get-user-posts/{userId}")
    ResponseEntity<ApiResponse> getUserPosts(@PathVariable  Long userId){
        try {
            User_ _user=userService.getUser(userId);
            List<Post> posts=userService.getUserPosts(_user);
            return ResponseEntity.ok().body(new ApiResponse(posts,"success"));
        } catch (ResourceNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(null,e.getMessage()));
        }
    };

    @DeleteMapping("/delete/{userId}")
    ResponseEntity<ApiResponse> deleteUser(@PathVariable  Long userId){
            String msg=userService.deleteUser(userId);
            if(msg!=null){
            return ResponseEntity.ok().body(new ApiResponse(msg,"success"));

            }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(null,"no user"));

            }

    };


};
