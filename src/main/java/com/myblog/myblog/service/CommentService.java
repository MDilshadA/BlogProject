package com.myblog.myblog.service;

import com.myblog.myblog.entity.Post;
import com.myblog.myblog.payload.CommentDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    public CommentDto createComment(long postId, CommentDto commentDto);

    void deleteCommentById(long postId, long commentId);

    List<CommentDto> getCommentByPostId(long postId);

    CommentDto updateComments(long commentId, CommentDto commentDto);

//    void deleteAllCommentByPostId(Long postId);
//    public void deleteCommentsByPost(Post post);
}
