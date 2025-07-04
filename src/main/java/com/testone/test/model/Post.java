package com.testone.test.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.mapping.GeneratorCreator;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String title;
    private String comment;
    private Date date;

    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonBackReference
    private  User_ user_;

    @PrePersist
    protected void onCreate(){
        date=new Date();
    }
    public Long getUserId(){
        if(this.getUser_()!=null){
        return this.getUser_().getUser_id();
        }
        return 0L;
    }


}
