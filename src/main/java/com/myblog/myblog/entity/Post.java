package com.myblog.myblog.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "title" ,unique = true)
    private String title;
    @Column(name = "description",unique = true)
    private  String description;
    @Column(name = "content",unique = true)
    private  String content;



    //Cascade if you delete the code automatically comment would be deleted

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL,orphanRemoval = true)
            //we are using List becz comment is many
    List<Comment> comments=new ArrayList<>();
}
