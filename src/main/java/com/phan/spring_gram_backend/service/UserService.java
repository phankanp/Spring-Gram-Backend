package com.phan.spring_gram_backend.service;

import com.phan.spring_gram_backend.exceptions.UsernameAlreadyExistsException;
import com.phan.spring_gram_backend.model.Followers;
import com.phan.spring_gram_backend.model.Post;
import com.phan.spring_gram_backend.model.User;
import com.phan.spring_gram_backend.repository.FollowersRepository;
import com.phan.spring_gram_backend.repository.PostRepository;
import com.phan.spring_gram_backend.repository.ProfileImageRepository;
import com.phan.spring_gram_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private FollowersRepository followersRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser(User user) {

        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new UsernameAlreadyExistsException("Email '" + user.getUsername() + "' is already in use");
        } else if (userRepository.findByAlias(user.getAlias()) != null) {
            throw new UsernameAlreadyExistsException("Username '" + user.getAlias() + "' is already in use");
        }

        try {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

            user.setConfirmPassword(" ");

            userRepository.save(user);

            return user;
        } catch (Exception e) {
            throw new NullPointerException();
        }
    }

    public Map<String, Object> editProfile(String username,  String fullName, String profileImageUrl) {
        User user2 = userRepository.getById(userRepository.findByUsername(username).getId());

        user2.setProfileImageUrl(profileImageUrl);
        user2.setId(user2.getId());
        user2.setFullName(fullName);
        userRepository.save(user2);

        User user = userRepository.findByAlias(userRepository.findByUsername(username).getAlias());

        return getStringObjectMap(user);
    }


    public Map<String, Object> getProfile(String userAlias) {
        User user = userRepository.findByAlias(userAlias);

        return getStringObjectMap(user);
    }

    private Map<String, Object> getStringObjectMap(User user) {
        Map<String, Object> profile = new HashMap<>();

        profile.put("userId", user.getId());
        profile.put("postCount", postRepository.findAllByUser(user).size());
        profile.put("followers", getFollowers(user));
        profile.put("following", getFollowing(user));
        profile.put("userAlias", user.getAlias());
        profile.put("posts", postRepository.findAllByUser(user));

        return profile;
    }

    public Map<String, Object> addFollow(Long toFollowUserId, String username) {

        followersRepository.save(setFollower(toFollowUserId, username));

        return getProfile(userRepository.getById(toFollowUserId).getAlias());
    }

    public void addFollowDBLoader(Long toFollowUserId, String username) {

        followersRepository.save(setFollower(toFollowUserId, username));
    }

    public Map<String, Object> removeFollow(Long toUnFollowUserId, String username) {

        User userUnfollower = userRepository.findByUsername(username);

        Followers follow = followersRepository.findByUserFollowerIdAndFollowingId(userUnfollower.getId(), toUnFollowUserId);

        followersRepository.delete(follow);

        return getProfile(userRepository.getById(toUnFollowUserId).getAlias());
    }

//    public byte[] getProfileImage(String userAlias) {
//
//        return userRepository.findByAlias(userAlias).getProfileImage();
//    }

    private List<Map<String, Object>> getFollowers(User user) {
        List<Map<String, Object>> followers = new ArrayList<>();

        for (Followers f : user.getFollowers()) {
            Map<String, Object> followersMap = new HashMap<>();
            followersMap.put("id", f.getId());
            followersMap.put("userAlias", f.getFollowersAlias());

            followers.add(followersMap);
        }

        return followers;
    }

    private List<Map<String, Object>> getFollowing(User user) {
        List<Map<String, Object>> following = new ArrayList<>();

        for (Followers f : user.getFollowing()) {
            Map<String, Object> followingMap = new HashMap<>();
            followingMap.put("id", f.getId());
            followingMap.put("userAlias", f.getFollowingAlias());

            following.add(followingMap);
        }

        return following;
    }

    private Followers setFollower(Long toFollowUserId, String username) {
        Followers follow = new Followers();

        User userFollower = userRepository.findByUsername(username);

        Optional<User> userToFollow = userRepository.findById(toFollowUserId);

        follow.setUserFollower(userFollower);
        follow.setFollowing(userToFollow.get());

        follow.setFollowersAlias(userFollower.getAlias());
        follow.setFollowingAlias(userToFollow.get().getAlias());

        return follow;
    }

    public String getUserProfileImageUrl(String userAlias) {
        return userRepository.findByAlias(userAlias).getProfileImageUrl();
    }
}