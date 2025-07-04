package com.testone.test.dto;

import com.testone.test.model.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoNoPosts {
    private Long user_id;

    private String username ;
    private String lastname ;
    private String email ;
    private String img ;
    private Boolean isLoggedIn;
    private int num_posts;

    public void onCreate(List<Post> posts){
        this.setNum_posts(posts.toArray().length);
    }

}
