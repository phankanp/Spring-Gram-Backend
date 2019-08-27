package com.phan.spring_gram_backend.repository;

import com.phan.spring_gram_backend.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
