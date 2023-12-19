package com.myblog.myblog.service.impl;

import com.myblog.myblog.entity.Post;
import com.myblog.myblog.exceptionHandling.ResourceNotFound;
import com.myblog.myblog.payload.PostDto;
import com.myblog.myblog.payload.PostResponse;
import com.myblog.myblog.repository.Postrepository;
import com.myblog.myblog.service.Postservice;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements Postservice {

    private Postrepository postrepo;
    private ModelMapper modelMapper;

//    Constructor based injection to inject simple JPA object into it
    public PostServiceImpl(Postrepository postrepo,ModelMapper modelMapper) {
        this.postrepo = postrepo;
        this.modelMapper=modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        //change Dto object to entity object
        Post post = mapToEntity(postDto);
        //save methods can work only on entity class
        // after save it,it return back as entity Object
        Post savedPost=postrepo.save(post);  //save method goes to repository layer
        //change entity object to dto object to send postman
        PostDto dto = mapToDto(savedPost);
        return dto;
    }

    @Override
    public void deleteById(Long id) {
    postrepo.findById(id).orElseThrow(
            ()->new ResourceNotFound("Post Not Found with Id :"+id)
    );
    postrepo.deleteById(id);
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = postrepo.findById(id).orElseThrow(
                () -> new ResourceNotFound("Post Not Found with id :" + id)
        );
        return mapToDto(post);
    }

    @Override
    public PostDto UpdatePost(Long id, PostDto dto) {
        Post post = postrepo.findById(id).orElseThrow(
                () -> new ResourceNotFound("Post Not Found with id :" + id)
        );
        post.setTitle(dto.getTitle());
        post.setDescription(dto.getDescription());
        post.setContent(dto.getContent());
        Post savePost = postrepo.save(post);
        PostDto postDto = mapToDto(savePost);
        return postDto;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        //here pageable has all information of provide by PageRequest.of() means pageNo,PageSize
//        Pageable pageable = PageRequest.of(pageNo,pageSize, Sort.by(sortBy));
        //for sorting in descending oreder
//        Pageable pageable = PageRequest.of(pageNo,pageSize, Sort.by(Sort.Order.desc(sortBy)));


        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable =PageRequest.of(pageNo,pageSize,sort);
        Page<Post> pagePostObjects = postrepo.findAll(pageable);
        List<Post> posts = pagePostObjects.getContent();
        List<PostDto> postDtos = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());



        PostResponse response =new PostResponse();
        response.setDto(postDtos);  //here setDto attribute declare in PostResponse class
        response.setPageNo(pagePostObjects.getNumber());
        response.setTotalPage(pagePostObjects.getTotalPages());
        response.setLastPage(pagePostObjects.isLast());
        response.setPageSize(pagePostObjects.getSize());

        return response;
    }

    @Override
    public Post findPostById(Long postId) {
        Post post = postrepo.findById(postId).get();
        return post;
    }

    Post mapToEntity(PostDto postDto){
        Post post=modelMapper.map(postDto,Post.class);
//        Post post=new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return post;
    }
    PostDto mapToDto(Post savePost){
        PostDto postDto=modelMapper.map(savePost,PostDto.class);
//        PostDto postDto=new PostDto();
//        postDto.setId(savePost.getId());
//        postDto.setTitle(savePost.getTitle());
//        postDto.setDescription(savePost.getDescription());
//        postDto.setContent(savePost.getContent());
        return postDto;
    }
}
