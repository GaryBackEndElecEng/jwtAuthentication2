package com.testone.test.repository;

import com.testone.test.model.User_;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User_,Long> {
    //@Query("select p from Post p where p.title=?1")
//    @NativeQuery("SELECT User_ FROM User_ where email=?1")
//    User_ findByEmail(String email);
    Optional<User_> findByEmail(String email);
}
