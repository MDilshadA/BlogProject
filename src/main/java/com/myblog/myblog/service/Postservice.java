package com.myblog.myblog.service;

import com.myblog.myblog.entity.Post;
import com.myblog.myblog.payload.PostDto;
import com.myblog.myblog.payload.PostResponse;

import java.util.List;

public interface Postservice {
    PostDto createPost(PostDto postDto);

    void deleteById(Long id);

    PostDto getPostById(Long id);

    PostDto UpdatePost(Long id, PostDto dto);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    Post findPostById(Long postId);
}
