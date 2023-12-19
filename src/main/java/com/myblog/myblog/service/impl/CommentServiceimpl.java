package com.myblog.myblog.service.impl;

import com.myblog.myblog.entity.Comment;
import com.myblog.myblog.entity.Post;
import com.myblog.myblog.exceptionHandling.ResourceNotFound;
import com.myblog.myblog.payload.CommentDto;
import com.myblog.myblog.repository.CommentRepository;
import com.myblog.myblog.repository.Postrepository;
import com.myblog.myblog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceimpl implements CommentService {
    private CommentRepository commentRepo;
    private ModelMapper modelMapper;
    private Postrepository postrepo;


    //dependency injection through constructor
    public CommentServiceimpl(CommentRepository commentRepo,
                              Postrepository postrepo,
                              ModelMapper modelMapper) {
        this.commentRepo = commentRepo;
        this.postrepo = postrepo;
        this.modelMapper=modelMapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        //to find post Id is find or not
        Post post = postrepo.findById(postId).orElseThrow(
                () -> new ResourceNotFound("page Not Found with id :" + postId)
        );

        Comment comment =MapToEntity(commentDto);
        //for which post this comments is used
        //for mapping post to comment
        comment.setPost(post);
        Comment c=commentRepo.save(comment);
        return MapToDto(c);
    }


    @Override
    public void deleteCommentById(long postId, long commentId) {
        //which comment based on id you wanna delete for which post based id
        postrepo.findById(postId).orElseThrow(
                () -> new ResourceNotFound("Post Not Found with id :" + postId)
        );
        commentRepo.findById(commentId).orElseThrow(
                () -> new ResourceNotFound("Comment Not Found with id :" + commentId)
        );
        commentRepo.deleteById(commentId);

    }

    @Override
    public List<CommentDto> getCommentByPostId(long postId) {
    //The method is expected to return a list of Comment entities associated with the specified postId.
        List<Comment> comments = commentRepo.findByPostId(postId);
     // findByPostId(postId) that declare in Repository layer user defined
        List<CommentDto> commentDto = comments.stream().map(list -> MapToDto(list)).collect(Collectors.toList());
        return  commentDto;
    }

    @Override
    public CommentDto updateComments(long commentId, CommentDto commentDto) {
        Comment comForPostId = commentRepo.findById(commentId).get();
        //here we get content that belongs to commentId
        //that content has post id also bcz mapping already done or Post declare in Comments
        Post post = postrepo.findById(comForPostId.getPost().getId()).get();
        //postId content=comForPostId.getPost().getId()
        //means we get post id from content found through CommentId Above
        Comment comment = MapToEntity(commentDto);
        comment.setId(commentId);
        //here we set commentId for update to avoid create new
        comment.setPost(post);
       //here we set post for setting post id
        Comment saveComment = commentRepo.save(comment);
        CommentDto dto = MapToDto(saveComment);
        return dto;
    }


    Comment MapToEntity(CommentDto commentDto){
        //Using Model Mapper
        Comment comment=modelMapper.map(commentDto,Comment.class);
//        Comment comment=new Comment();
//        comment.setName(commentDto.getName());
//        comment.setBody(commentDto.getBody());
//        comment.setEmail(commentDto.getEmail());
        return comment;

    }


    CommentDto MapToDto(Comment comment){
        //using model mapper
        CommentDto commentDto=modelMapper.map(comment,CommentDto.class);
//        CommentDto commentDto=new CommentDto();
//        commentDto.setName(comment.getName());
//        commentDto.setBody(comment.getBody());
//        commentDto.setEmail(comment.getEmail());
        return commentDto;

    }
}
