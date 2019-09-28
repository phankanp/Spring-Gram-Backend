package com.phan.spring_gram_backend.repository;

import com.phan.spring_gram_backend.model.Followers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowersRepository extends JpaRepository<Followers, Long> {

    Followers findByUserFollowerIdAndFollowingId(Long followers_user_id, Long following_user_id);
}
