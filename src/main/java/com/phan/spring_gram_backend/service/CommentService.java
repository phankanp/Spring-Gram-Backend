package com.phan.spring_gram_backend.service;

import com.phan.spring_gram_backend.exceptions.CommentNotFoundException;
import com.phan.spring_gram_backend.exceptions.PostNotFoundException;
import com.phan.spring_gram_backend.model.Comment;
import com.phan.spring_gram_backend.model.Post;
import com.phan.spring_gram_backend.model.User;
import com.phan.spring_gram_backend.repository.CommentRepository;
import com.phan.spring_gram_backend.repository.PostRepository;
import com.phan.spring_gram_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    public List<Comment> getAllCommentsForPost(Long postId) {
        List<Comment> allPostComments = commentRepository.findByPostId(postId);

        return allPostComments;
    }

    public List<Comment> saveComment(Comment comment, Long postId, String username) {

        User user = userRepository.findByUsername(username);

        Optional<Post> commentToPost = postRepository.findById(postId);

        if (!commentToPost.isPresent()) {
            throw new PostNotFoundException("The post with ID: '" + postId + "' does not exists");
        }

        comment.setUser(user);
        comment.setUserAlias(user.getAlias());
        comment.setPost(commentToPost.get());

        commentRepository.save(comment);

        return commentRepository.findByPostId(postId);
    }

    public List<Comment> deleteComment(Long commentId, Long postId) {
        Optional<Comment> commentToDelete = commentRepository.findByIdAndPostId(commentId, postId);

        if (!commentToDelete.isPresent()) {
            throw new CommentNotFoundException("The comment with ID '" + commentId + "' does not exist");
        }

        commentRepository.delete(commentToDelete.get());

        return commentRepository.findByPostId(postId);
    }
}
