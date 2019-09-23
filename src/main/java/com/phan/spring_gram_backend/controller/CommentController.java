package com.phan.spring_gram_backend.controller;

import com.phan.spring_gram_backend.model.Comment;
import com.phan.spring_gram_backend.service.CommentService;
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
@RequestMapping("/api/post")
@CrossOrigin
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    private ValidationErrorService validationErrorService;


    @GetMapping("/{postId}/comments")
    public List<Comment> getAllCommentsForPost(@PathVariable Long postId) {

        return commentService.getAllCommentsForPost(postId);
    }


    @PostMapping("/{postId}/comments")
    public ResponseEntity<?> addCommentToPost(@Valid @RequestBody Comment comment, BindingResult result, @PathVariable Long postId,
                                              Principal principal) {

        ResponseEntity<?> errorMap = validationErrorService.MapValidationService(result);
        if (errorMap != null) {
            return errorMap;
        }

        Comment comment1 = commentService.saveComment(comment, postId, principal.getName());

        return new ResponseEntity<Comment>(comment1, HttpStatus.CREATED);
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<?> deleteCommentFromPost(@PathVariable(value = "postId") Long postId,
                                                   @PathVariable(value = "commentId") Long commentId) {

        commentService.deleteComment(commentId, postId);

        return new ResponseEntity<String>("Comment with ID: '" + commentId + "' was deleted", HttpStatus.OK);
    }
}
