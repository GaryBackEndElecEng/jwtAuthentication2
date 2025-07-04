package com.testone.test.service;

import com.testone.test.dto.PostDto;
import com.testone.test.model.Post;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostDtoService {

    public PostDto convertPostDto(Post post){
        PostDto postDto=new PostDto();
        postDto.setId(post.getId());
        postDto.setDate(post.getDate());
        postDto.setComment(post.getComment());
        postDto.setTitle(post.getTitle());
        postDto.setUser_id(post.getUserId());
        return postDto;
    }

    public List<PostDto> convertPostDtos(List<Post> posts){
        List<PostDto> postDtos=new ArrayList<>();
        posts.forEach(post->{
            postDtos.add(convertPostDto(post));
        });
        return postDtos;
    }
}
