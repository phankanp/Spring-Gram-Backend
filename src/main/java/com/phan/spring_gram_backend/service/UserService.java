package com.phan.spring_gram_backend.service;

import com.phan.spring_gram_backend.exceptions.UsernameAlreadyExistsException;
import com.phan.spring_gram_backend.model.User;
import com.phan.spring_gram_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser(User user) {

        try {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

            user.setUsername(user.getUsername());

            user.setConfirmPassword(" ");

            return userRepository.save(user);
        } catch (Exception e) {
            throw new UsernameAlreadyExistsException("Email '" + user.getUsername() + "' is already in use");
        }
    }
}
