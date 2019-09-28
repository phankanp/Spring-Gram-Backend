package com.phan.spring_gram_backend.repository;

import com.phan.spring_gram_backend.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    Like findByPostIdAndUserId(Long postId, Long userId);

    List<Like> findByPostId(Long postId);
}
