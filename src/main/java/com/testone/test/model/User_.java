package com.testone.test.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User_ implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;
    private String password;
    private String username ;
    private String firstName;
    private String lastname ;
    private String email ;
    private String img ;
    private Integer num_posts=0;
    LocalDateTime createdAt;
    private LocalDateTime updateAt;
    @Enumerated(EnumType.STRING)
    Role role;


    @OneToMany(mappedBy = "user_",fetch=FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)

    private List<Post> posts;

    @PrePersist
    protected void onCreate(){
        this.setNum_posts(this.posts.toArray().length);
        createdAt=LocalDateTime.now();
        updateAt=LocalDateTime.now();
    }

    public void addPost(Post post){
        this.posts.add(post);
        post.setUser_(this);

    };

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return convertRoleToCollectionAuthority(role);
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    public Collection<? extends GrantedAuthority> convertRoleToCollectionAuthority(Role strCollect){
        String authorityString = ","+strCollect +",";
        return AuthorityUtils.commaSeparatedStringToAuthorityList(authorityString);
    }

}
