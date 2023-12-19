package com.myblog.myblog.repository;

import com.myblog.myblog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Custom methods if needed

    Optional<User> findByEmail(String email);
    Optional<User> findByUsernameOrEmail(String username,String email);

    Optional<User> findByUsername(String username);
    //it will act like-->select * from user where username="username";
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

}
