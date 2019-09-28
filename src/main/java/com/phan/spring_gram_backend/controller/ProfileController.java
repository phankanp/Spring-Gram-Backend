package com.phan.spring_gram_backend.controller;

import com.phan.spring_gram_backend.repository.FollowersRepository;
import com.phan.spring_gram_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/profile")
@CrossOrigin
public class ProfileController {

    @Autowired
    UserService userService;

    @Autowired
    FollowersRepository followersRepository;

    @GetMapping("/user/{userAlias}")
    public ResponseEntity<?> getAllPosts(@PathVariable String userAlias) {
        return ResponseEntity.ok(userService.getProfile(userAlias));
    }

    @PostMapping("/follow/{userId}")
    public ResponseEntity<?> addFollow(@PathVariable(value = "userId") Long userId, Principal principal) {

        return new ResponseEntity<>(userService.addFollow(userId, principal.getName()), HttpStatus.OK);
    }

    @DeleteMapping("/unfollow/{userId}")
    public ResponseEntity<?> removeFollow(@PathVariable(value = "userId") Long userId, Principal principal) {
        System.out.println("test");
        return new ResponseEntity<>(userService.removeFollow(userId, principal.getName()), HttpStatus.OK);
    }
}
