package com.phan.spring_gram_backend.repository;

import com.phan.spring_gram_backend.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
