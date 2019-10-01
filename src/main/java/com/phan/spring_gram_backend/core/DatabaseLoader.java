package com.phan.spring_gram_backend.core;

import com.phan.spring_gram_backend.model.Comment;
import com.phan.spring_gram_backend.model.Like;
import com.phan.spring_gram_backend.model.Post;
import com.phan.spring_gram_backend.model.User;
import com.phan.spring_gram_backend.repository.LikeRepository;
import com.phan.spring_gram_backend.repository.PostRepository;
import com.phan.spring_gram_backend.repository.ProfileImageRepository;
import com.phan.spring_gram_backend.service.CommentService;
import com.phan.spring_gram_backend.service.PostService;
import com.phan.spring_gram_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Component
public class DatabaseLoader implements ApplicationRunner {

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @Autowired
    CommentService commentService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    LikeRepository likeRepository;

    @Autowired
    ProfileImageRepository profileImageRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        LocalDateTime createDateTime = LocalDateTime.now();
        LocalDateTime updateDateTime = LocalDateTime.now();

        User testUser1 = new User("testUser1@gmail.com", "testUserOne", "Test User One", "password", "password", Files.readAllBytes(Paths.get("./src/main/resources/static/images/profile images/rmu1" +
                ".jpg")));
        User testUser2 = new User("testUser2@gmail.com", "testUserTwo", "Test User Two", "password", "password", Files.readAllBytes(Paths.get("./src/main/resources/static/images/profile images/rmu2" +
                ".jpg")));
        User testUser3 = new User("testUser3@gmail.com", "testUserThree", "Test User Two", "password", "password", Files.readAllBytes(Paths.get("./src/main/resources/static/images/profile images/rmu3" +
                ".jpg")));

        userService.saveUser(testUser1);
        userService.saveUser(testUser2);
        userService.saveUser(testUser3);


        Post p1 = new Post(
                Files.readAllBytes(Paths.get("./src/main/resources/static/images/yosemite" +
                        ".jpg")),
                "Yosemite National Park"
        );

        p1.setUser(testUser1);
        p1.setUserAlias(testUser1.getAlias());


        postRepository.save(p1);

        Comment testComment1 = new Comment();
        Comment testComment2 = new Comment();

        testComment1.setComment("TestComment");
        testComment2.setComment("TestComment");

        commentService.saveComment(testComment1, p1.getId(), testUser1.getUsername());

        userService.addFollowDBLoader(testUser1.getId(), testUser2.getUsername());
        userService.addFollowDBLoader(testUser1.getId(), testUser3.getUsername());
        userService.addFollowDBLoader(testUser2.getId(), testUser1.getUsername());
        userService.addFollowDBLoader(testUser3.getId(), testUser1.getUsername());

        Like l1 = new Like();

        l1.setPost(p1);
        l1.setUser(testUser1);
        l1.setUsername(testUser1.getUsername());

        likeRepository.save(l1);


    }
}
