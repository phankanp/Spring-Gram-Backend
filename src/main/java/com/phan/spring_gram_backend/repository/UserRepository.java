package com.phan.spring_gram_backend.repository;

import com.phan.spring_gram_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findByAlias(String alias);

    User getById(Long id);

    Optional<User> findById(Long id);
}
