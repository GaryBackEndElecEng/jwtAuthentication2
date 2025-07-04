package com.testone.test.service;

import com.testone.test.dto.PostDto;
import com.testone.test.exception.ResourceNotFound;
import com.testone.test.model.Post;
import com.testone.test.model.User_;
import com.testone.test.repository.PostRepo;
import com.testone.test.repository.UserRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class PostService implements IPostService{

    private final PostRepo postRepo;
    private final UserRepo userRepo;
    private final PostDtoService postDtoService;
    private final JwtService jwtService;
    @Autowired
    public HttpServletRequest request;

    @Override
    public List<PostDto> getAllPosts() {
        return postDtoService.convertPostDtos( postRepo.findAll());
    }

    @Override
    public List<PostDto> getPostsByTitle(String title) {
        return postDtoService.convertPostDtos(postRepo.findAllByTitle(title));
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post=postRepo.findById(id).orElseThrow(()->new ResourceNotFound(" no resource"));
        if(post !=null){
            return postDtoService.convertPostDto(post);
        }
        return new PostDto();
    }

    @Override
    public PostDto savePost(Post post, Long userId) {
        //GET USER,IF NO USER=>user_id=null=> save post

        log.debug("this is savePost()-{}",request);
        System.out.println("REQUEST:=>" +request);

        String userEmail=jwtService.extractEmail(request);
        User_ user=userRepo.findById(userId)
                .stream().findFirst()
                        .filter(user_->user_.getEmail().equals(userEmail))
                .orElseThrow(()->new ResourceNotFound("no user by ID OR not authorized"));
        if(user!=null){
            user.addPost(post);
            return postDtoService.convertPostDto(postRepo.save(post));
        }
        return null;
        //if NO user then get
    }

    @Override
    public PostDto getPostByTitle(String title) {
        return postDtoService.convertPostDto(postRepo.findByTitle(title));
    }

    @Override
    public String deletePost(Long postId,Long userId) {
        Post post=postRepo.findById(postId).stream().findFirst().orElse(null);
        User_ user=userRepo.findById(userId).stream().findFirst().orElse(null);
        if(post!=null && user!=null){
            postRepo.delete(post);
            return "deleted: " + post.getId();
        }else{
            return null;
        }

    }

    @Override
    public PostDto updatePost(Post post, Long userId) {
       boolean isUser= userRepo.findById(userId).isPresent();
       if(isUser){
           postRepo.save(post);
       }
            return postDtoService.convertPostDto(post);

    }

    @Override
    public Boolean verifyUser(Long userId){
        User_ user=userRepo.findById(userId).stream().findFirst().orElse(null);
        return user != null;
    }




}
