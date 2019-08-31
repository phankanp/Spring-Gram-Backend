package com.phan.spring_gram_backend.repository;

import com.phan.spring_gram_backend.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {


}