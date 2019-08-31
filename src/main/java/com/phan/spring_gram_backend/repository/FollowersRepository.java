package com.phan.spring_gram_backend.repository;

import com.phan.spring_gram_backend.model.Followers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowersRepository extends JpaRepository<Followers, Long> {

    Followers findByFollowersIdAndFollowingId(Long followers_user_id, Long following_user_id);

    List<Followers> findByFollowingId(Long following_user_id);

    List<Followers> findByFollowersId(Long followers_user_id);
}
