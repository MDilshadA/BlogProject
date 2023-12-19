package com.myblog.myblog.repository;

import com.myblog.myblog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Postrepository extends JpaRepository<Post, Long> {

//    void deleteByPostId(Long postId);
}
