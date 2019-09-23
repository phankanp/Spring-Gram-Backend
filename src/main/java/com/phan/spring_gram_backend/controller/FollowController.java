package com.phan.spring_gram_backend.controller;

import com.phan.spring_gram_backend.repository.FollowersRepository;
import com.phan.spring_gram_backend.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/follow")
@CrossOrigin
public class FollowController {

    @Autowired
    FollowService followService;

    @Autowired
    FollowersRepository followersRepository;


    @PostMapping("/{userId}")
    public ResponseEntity<?> addFollow(@PathVariable(value = "userId") Long userId, Principal principal) {

        followService.addFollow(userId, principal.getName());


        return new ResponseEntity<String>("Successfully followed user with ID: '" + userId + "'", HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> removeFollow(@PathVariable(value = "userId") Long userId, Principal principal) {

        followService.removeFollow(userId, principal.getName());

        return new ResponseEntity<String>("Successfully unfollowed user with ID: '" + userId + "'", HttpStatus.OK);
    }

    @GetMapping("/{userId}/count")
    public ResponseEntity<?> getFollowerAndFollowingCount(@PathVariable(value = "userId") Long userId) {

        Map<String, Integer> countMap = followService.getFollowersAndFollowingCount(userId);

        return new ResponseEntity<>(countMap, HttpStatus.OK);
    }
}
