package com.phan.spring_gram_backend.controller;

import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.phan.spring_gram_backend.config.CloudinaryConfig;
import com.phan.spring_gram_backend.model.User;
import com.phan.spring_gram_backend.repository.FollowersRepository;
import com.phan.spring_gram_backend.service.UserService;
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
import java.util.Map;

@RestController
@RequestMapping("/api/profile")
@CrossOrigin
public class ProfileController {

    @Autowired
    UserService userService;

    @Autowired
    FollowersRepository followersRepository;

    @Autowired
    CloudinaryConfig cloudinaryConfig;

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

        return new ResponseEntity<>(userService.removeFollow(userId, principal.getName()), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> editProfile(@Valid User user, BindingResult result, MultipartFile profileImage, Principal principal) {

        String profileImageUrl = "";

        try {
            Map uploadResult =  cloudinaryConfig.upload(profileImage.getBytes(), ObjectUtils.asMap(
                    "resourcetype", "auto",
                    "transformation", new Transformation().width(854).height(576),
                    "folder", "profile_images"
            ));
            profileImageUrl = (String) uploadResult.get("url");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(userService.editProfile(principal.getName(), user.getFullName(), profileImageUrl), HttpStatus.OK);
    }

//    @GetMapping(value = "/{userAlias}/image", produces = MediaType.IMAGE_JPEG_VALUE)
//    @ResponseBody
//    public byte[] profileImage(@PathVariable String userAlias) {
//
//        return userService.getProfileImage(userAlias);
//    }

    @GetMapping(value = "/{userAlias}/imageUrl")
    public String getProfileImageUrl(@PathVariable String userAlias) {

        return userService.getUserProfileImageUrl(userAlias);
    }
}