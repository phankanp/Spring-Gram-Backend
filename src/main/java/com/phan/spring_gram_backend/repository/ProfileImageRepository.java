package com.phan.spring_gram_backend.repository;

import com.phan.spring_gram_backend.model.ProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {

//    ProfileImage findByUserId(Long id);
}
