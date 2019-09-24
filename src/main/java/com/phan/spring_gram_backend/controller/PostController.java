package com.phan.spring_gram_backend.controller;

import com.phan.spring_gram_backend.model.Like;
import com.phan.spring_gram_backend.model.Post;
import com.phan.spring_gram_backend.service.PostService;
import com.phan.spring_gram_backend.service.ValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/post")
@CrossOrigin
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private ValidationErrorService validationErrorService;

    @GetMapping("/")
    public ResponseEntity<?> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPost());
    }

    @GetMapping("{postId}")
    public ResponseEntity<?> getPostById(@PathVariable Long postId) {

        Post post = postService.getPostById(postId);

        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @GetMapping(value = "/{postId}/image", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] postImage(@PathVariable Long postId) {

        return postService.getPostById(postId).getImage();
    }

    @PostMapping("")
    public ResponseEntity<?> createNewPosts(@Valid Post post, BindingResult result, @RequestPart("file") MultipartFile imageFile, Principal principal) {

        ResponseEntity<?> errorMap = validationErrorService.MapValidationService(result);
        if (errorMap != null) {
            return errorMap;
        }

        Post post1 = postService.saveOrUpdatePost(post, imageFile, principal.getName());

        return new ResponseEntity<Post>(post1, HttpStatus.CREATED);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId, Principal principal) {

        postService.deletePost(postId, principal.getName());

        return new ResponseEntity<String>("Post with ID: '" + postId + "' was deleted", HttpStatus.OK);
    }

    @PutMapping("/like/{postId}")
    public ResponseEntity<?> likePost(@PathVariable Long postId, Principal principal) {

        List<Like> likes = postService.likePost(postService.getPostById(postId), principal);

        return ResponseEntity.ok(likes);
    }

    @DeleteMapping("/unlike/{postId}")
    public ResponseEntity<?> unlikePost(@PathVariable Long postId, Principal principal) {

        List<Like> likes = postService.unlikePost(postService.getPostById(postId), principal);

        return ResponseEntity.ok(likes);
    }

}
