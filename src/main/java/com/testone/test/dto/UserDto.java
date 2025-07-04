package com.testone.test.dto;

import com.testone.test.model.Post;
import com.testone.test.model.User_;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto extends User_ {
    private Boolean isLoggedIn;



    public void onCreate(){
        this.setNum_posts(this.getPosts().toArray().length);
    }
}
