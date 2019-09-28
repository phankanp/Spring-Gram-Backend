package com.phan.spring_gram_backend.service;

import com.phan.spring_gram_backend.exceptions.UsernameAlreadyExistsException;
import com.phan.spring_gram_backend.model.Followers;
import com.phan.spring_gram_backend.model.Post;
import com.phan.spring_gram_backend.model.User;
import com.phan.spring_gram_backend.repository.FollowersRepository;
import com.phan.spring_gram_backend.repository.PostRepository;
import com.phan.spring_gram_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

    public Map<String, Object> getProfile(String userAlias) {
        User user = userRepository.findByAlias(userAlias);

        List<Post> posts = postRepository.findAllByUser(user);

        Map<String, Object> profile = new HashMap<>();
        List<Map<String, Object>> followers = new ArrayList<>();
        List<Map<String, Object>> following = new ArrayList<>();


        for (Followers f : user.getFollowers()) {
            Map<String, Object> followersMap = new HashMap<>();
            followersMap.put("id", f.getId());
            followersMap.put("userAlias", f.getFollowersAlias());

            followers.add(followersMap);
        }

        for (Followers f : user.getFollowing()) {
            Map<String, Object> followingMap = new HashMap<>();
            followingMap.put("id", f.getId());
            followingMap.put("userAlias", f.getFollowingAlias());

            following.add(followingMap);
        }

        profile.put("userId", user.getId());
        profile.put("postCount", postRepository.findAllByUser(user).size());
        profile.put("followers", followers);
        profile.put("following", following);
        profile.put("posts", posts);

        return profile;
    }

    public Map<String, Object> addFollow(Long toFollowUserId, String username) {
        Followers follow = new Followers();

        User userFollower = userRepository.findByUsername(username);

        Optional<User> userToFollow = userRepository.findById(toFollowUserId);

        follow.setUserFollower(userFollower);
        follow.setFollowing(userToFollow.get());

        follow.setFollowersAlias(userFollower.getAlias());
        follow.setFollowingAlias(userToFollow.get().getAlias());

        followersRepository.save(follow);

        return getProfile(userFollower.getAlias());

    }

    public void addFollowDBLoader(Long toFollowUserId, String username) {
        Followers follow = new Followers();

        User userFollower = userRepository.findByUsername(username);

        Optional<User> userToFollow = userRepository.findById(toFollowUserId);

        follow.setUserFollower(userFollower);
        follow.setFollowing(userToFollow.get());

        follow.setFollowersAlias(userFollower.getAlias());
        follow.setFollowingAlias(userToFollow.get().getAlias());

        followersRepository.save(follow);
    }

    public Map<String, Object> removeFollow(Long toUnFollowUserId, String username) {

        User userUnfollower = userRepository.findByUsername(username);

        Followers follow = followersRepository.findByUserFollowerIdAndFollowingId(userUnfollower.getId(), toUnFollowUserId);

        followersRepository.delete(follow);

        return getProfile(userUnfollower.getAlias());
    }

}
