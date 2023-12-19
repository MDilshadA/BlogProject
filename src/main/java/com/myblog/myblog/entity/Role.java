package com.myblog.myblog.entity;
import lombok.*;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users ;

//    private Set<User> users = new HashSet<>();

    // Constructors, getters, setters, and other methods
    // Omitted for brevity
}
