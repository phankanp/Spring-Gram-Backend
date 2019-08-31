package com.phan.spring_gram_backend.service;

import com.phan.spring_gram_backend.model.Followers;
import com.phan.spring_gram_backend.model.User;
import com.phan.spring_gram_backend.repository.FollowersRepository;
import com.phan.spring_gram_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class FollowService {

    @Autowired
    FollowersRepository followersRepository;

    @Autowired
    UserRepository userRepository;

    public void addFollow(Long toFollowUserId, String username) {
        Followers follow = new Followers();

        User userFollower = userRepository.findByUsername(username);

        Optional<User> userToFollow = userRepository.findById(toFollowUserId);

        follow.setFollowers(userFollower);
        follow.setFollowing(userToFollow.get());

        followersRepository.save(follow);
    }

    public void removeFollow(Long toUnFollowUserId, String username) {

        User userUnfollower = userRepository.findByUsername(username);

        Followers follow = followersRepository.findByFollowersIdAndFollowingId(userUnfollower.getId(), toUnFollowUserId);

        followersRepository.delete(follow);
    }

    public Map<String, Integer> getFollowersAndFollowingCount(Long userId) {

        Optional<User> user = userRepository.findById(userId);

        Integer followers = followersRepository.findByFollowingId(user.get().getId()).size();

        Integer following = followersRepository.findByFollowersId(user.get().getId()).size();

        Map<String, Integer> countMap = new HashMap<>();

        countMap.put("followers", followers);
        countMap.put("following", following);

        return countMap;
    }
}
