package com.myblog.myblog.controller;

import com.myblog.myblog.entity.Post;
import com.myblog.myblog.payload.PostDto;
import com.myblog.myblog.payload.PostResponse;
import com.myblog.myblog.service.CommentService;
import com.myblog.myblog.service.Postservice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RestController
@RequestMapping("/api/posts")
public class PostController {
    /**
     * postServeice help us To connect service layer of project
     */
    private Postservice postService;
    private CommentService commentService;


    //To Avoid @Autowired constructor based injection
    public PostController(Postservice postService,CommentService commentService) {
        this.postService = postService;
        this.commentService=commentService;
    }

    /**
     * here postDto comes from Postman as JSON Object
     */

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto, BindingResult result){
        //@RequestBody-bind http request body to entity object
        //@valid is used to validate the incoming data(JSON,XML)
        // if there are validation errors occur during data binding, they will be captured in the BindingResult
        //@valid @ModelAttribute BindingResult all comes together but not mandatory
        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        PostDto dto = postService.createPost(postDto);
    return new ResponseEntity<>(dto, HttpStatus.CREATED);
       /*
         here first data will go for save and after that save data will come back to controller
         and than it back to Postmn
        */
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        postService.deleteById(id);
        return new ResponseEntity<>("Post is deleted",HttpStatus.OK);

    }

//    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<PostDto>getPostByid(@PathVariable Long id){
        PostDto postDto = postService.getPostById(id);
        return  new ResponseEntity<>(postDto,HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto>UpadatePost(
            @PathVariable Long id,
            @RequestBody PostDto dto
    ){
        PostDto postDto=postService.UpdatePost(id,dto);
        return  new ResponseEntity<>(postDto,HttpStatus.OK);
    }

//    http://localhost:8080/api/posts?pageNo=0&pageSize=3&sortBy=descrption&sortDir=desc
    //Pagination in Spring Boot
//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(name = "pageNo",required = false,defaultValue = "0") int pageNo,
            @RequestParam(name = "pageSize",required = false,defaultValue = "3") int pageSize,
            @RequestParam(name = "sortBy",required = false,defaultValue = "id") String sortBy,
            @RequestParam(name = "sortDir",required = false, defaultValue = "asc") String sortDir
    ){
        PostResponse response = postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
        return response;
    }



}
