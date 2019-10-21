package com.phan.spring_gram_backend.controller;

import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.phan.spring_gram_backend.config.CloudinaryConfig;
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
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/post")
@CrossOrigin
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private ValidationErrorService validationErrorService;

    @Autowired
    CloudinaryConfig cloudinaryConfig;

    @GetMapping("/")
    public ResponseEntity<?> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPost());
    }

    @GetMapping("{postId}")
    public ResponseEntity<?> getPostById(@PathVariable Long postId) {

        Post post = postService.getPostById(postId);

        return new ResponseEntity<>(post, HttpStatus.OK);
    }

//    @GetMapping(value = "/{postId}/image", produces = MediaType.IMAGE_JPEG_VALUE)
//    @ResponseBody
//    public byte[] postImage(@PathVariable Long postId) {
//
//        return postService.getPostById(postId).getImage();
//    }

    @PostMapping("/")
    public ResponseEntity<?> createNewPosts(@Valid Post post, BindingResult result, MultipartFile image, Principal principal) {

        Map<String, String> errorMap = new HashMap<>();

        if (image == null && post.getCaption().equals("null")) {
            errorMap.put("image", "Must choose an image");
            errorMap.put("caption", "Must enter a caption");
            return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.BAD_REQUEST);
        } else if (image == null && !post.getCaption().equals("null")) {
            errorMap.put("image", "Must choose an image");
            return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.BAD_REQUEST);
        } else if (image != null && post.getCaption().equals("null")) {
            errorMap.put("caption", "Must enter a caption");
            return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.BAD_REQUEST);
        }

        String imageUrl = "";

        try {
            Map uploadResult =  cloudinaryConfig.upload(image.getBytes(), ObjectUtils.asMap(
                    "resourcetype", "auto",
                    "transformation", new Transformation().width(854).height(576),
                    "folder", "post_images"
            ));
            imageUrl = (String) uploadResult.get("url");
        } catch (IOException e) {
            e.printStackTrace();
            errorMap.put("message", "Upload failed");
        }

        Post post1 = postService.saveOrUpdatePost(post, imageUrl, principal.getName());

        return new ResponseEntity<>(post1, HttpStatus.CREATED);
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
