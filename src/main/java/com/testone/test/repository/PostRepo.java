package com.testone.test.repository;

import com.testone.test.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PostRepo  extends JpaRepository<Post,Long> {
    @NativeQuery("SELECT * FROM POST WHERE TITLE LIKE %?%1")
    List<Post> findAllByTitle(String title);
    @Query("select p from Post p where p.title=?1")
    Post findByTitle(String title);
};
