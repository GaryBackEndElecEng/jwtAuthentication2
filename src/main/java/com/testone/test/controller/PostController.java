package com.testone.test.controller;

import com.testone.test.model.Post;
import com.testone.test.response.ApiResponse;
import com.testone.test.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    @Autowired
    public PostService postService;

    @GetMapping("")
    public ResponseEntity<ApiResponse> getAllposts(){
        try {
            return ResponseEntity.ok().body(new ApiResponse(postService.getAllPosts(),"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(null,e.getMessage()));
        }
    };
    @GetMapping("/posts-title")
    public ResponseEntity<ApiResponse> getPostsByTitle(@RequestParam String title){
        try {
            return ResponseEntity.ok().body(new ApiResponse(postService.getPostsByTitle(title),"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(null,e.getMessage()));
        }
    }
    @GetMapping("/post-title")
    public ResponseEntity<ApiResponse> getPostByTitle(@RequestParam String title){
        try {
            return ResponseEntity.ok().body(new ApiResponse(postService.getPostByTitle(title),"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(null,e.getMessage()));
        }
    };
    @GetMapping("/post/{postId}")
    public ResponseEntity<ApiResponse> getPostById(@PathVariable Long postId){
        try {
            return ResponseEntity.ok().body(new ApiResponse(postService.getPostById(postId),"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(null,e.getMessage()));
        }
    };
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/post/{userId}")
    public ResponseEntity<ApiResponse> getSavePost(@RequestBody Post post, @PathVariable Long userId){
        try {
            return ResponseEntity.ok().body(new ApiResponse(postService.savePost(post,userId),"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(null,e.getMessage()));
        }
    };
    @PreAuthorize("hasRole('USER','ADMIN')")
    @PutMapping("/post/{userId}")
    public ResponseEntity<ApiResponse> getUpdatePost(@RequestBody Post post, @PathVariable Long userId){
        try {
            return ResponseEntity.ok().body(new ApiResponse(postService.updatePost(post,userId),"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(null,e.getMessage()));
        }
    };
    @PreAuthorize("hasRole('USER','ADMIN')")
    @DeleteMapping("/post/delete/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Long postId, @RequestParam Long userId){
        try {
            String deleteMsg=postService.deletePost(postId,userId);
            if(deleteMsg !=null){
            return ResponseEntity.ok().body(new ApiResponse(deleteMsg,"success"));
            }else{
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ApiResponse(null,"post is not a user"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(null,e.getMessage()));
        }
    };
}
