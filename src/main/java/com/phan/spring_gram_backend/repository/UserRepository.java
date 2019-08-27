package com.phan.spring_gram_backend.repository;

import com.phan.spring_gram_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
