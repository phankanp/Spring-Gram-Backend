package com.phan.spring_gram_backend.service;

import com.phan.spring_gram_backend.exceptions.PostNotFoundException;
import com.phan.spring_gram_backend.model.Post;
import com.phan.spring_gram_backend.model.User;
import com.phan.spring_gram_backend.repository.PostRepository;
import com.phan.spring_gram_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;


    public List<Post> getAllPost() {
       return postRepository.findAll();
    }

    public Post getPostById(Long postId) {
        Optional<Post> findPost = postRepository.findById(postId);

        if (!findPost.isPresent()) {
            throw new PostNotFoundException("The post with ID: '" + postId.toString() + "' cannot be updated because it does not exists");
        }

        return findPost.get();
    }

    public Post saveOrUpdatePost(Post post, String username) {
        if (post.getId() != null) {
            Optional<Post> existingPost = postRepository.findById(post.getId());

            if (existingPost.isPresent() && (!existingPost.get().getUser().getUsername().equals(username))) {
                throw new PostNotFoundException("The post was not found in you account, you can only edit post created by you");
            } else if (!existingPost.isPresent()) {
                throw new PostNotFoundException("The post with ID: '" + post.getId() + "' cannot be updated because it does not exists");
            }
        }

        User user = userRepository.findByUsername(username);

        post.setUser(user);

        return postRepository.save(post);
    }

    public void deletePost(Long postId, String username) {
        Optional<Post> postToDelete = postRepository.findById(postId);

        if (postToDelete.isPresent() && (!postToDelete.get().getUser().getUsername().equals(username))) {
            throw new PostNotFoundException("You can only delete a post created by you");
        } else if (!postToDelete.isPresent()) {
            throw new PostNotFoundException("The post with ID: '" + postId + "' cannot be deleted because it does not exists");
        }

        postRepository.delete(postToDelete.get());
    }
}
