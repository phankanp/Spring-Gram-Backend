package com.phan.spring_gram_backend.controller;

import com.phan.spring_gram_backend.model.Post;
import com.phan.spring_gram_backend.service.PostService;
import com.phan.spring_gram_backend.service.ValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/post/")
@CrossOrigin
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private ValidationErrorService validationErrorService;

    @GetMapping("/all")
    public List<Post> getAllPosts() {
        return postService.getAllPost();
    }

    @GetMapping("{postId}")
    public ResponseEntity<?> getPostById(@PathVariable Long postId) {

        Post post = postService.getPostById(postId);

        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> createNewPosts(@Valid @RequestBody Post post, BindingResult result, Principal principal) {

        ResponseEntity<?> errorMap = validationErrorService.MapValidationService(result);
        if (errorMap != null) {
            return errorMap;
        }

        Post post1 = postService.saveOrUpdatePost(post, principal.getName());

        return new ResponseEntity<Post>(post, HttpStatus.CREATED);
    }

    @DeleteMapping("{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId, Principal principal) {

        postService.deletePost(postId, principal.getName());

        return new ResponseEntity<String>("Post with ID: '" + postId + "' was deleted", HttpStatus.OK);
    }

}
