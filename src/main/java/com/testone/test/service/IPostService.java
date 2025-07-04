package com.testone.test.service;

import com.testone.test.dto.PostDto;
import com.testone.test.model.Post;

import java.util.List;

public interface IPostService {

    public List<PostDto> getAllPosts();
    public List<PostDto> getPostsByTitle(String title);
    public PostDto getPostById(Long id);
    public PostDto savePost(Post post,Long UserId);
    public PostDto getPostByTitle(String title);
    public String deletePost(Long postId,Long UserId);
    public PostDto updatePost(Post post,Long userId);
    Boolean verifyUser(Long userId);
}
