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

        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new UsernameAlreadyExistsException("Email '" + user.getUsername() + "' is already in use");
        } else if (userRepository.findByAlias(user.getAlias()) != null) {
            throw new UsernameAlreadyExistsException("Username '" + user.getAlias() + "' is already in use");
        }

        try {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

            user.setConfirmPassword(" ");

            userRepository.save(user);

            return user;
        } catch (Exception e) {
            throw new NullPointerException();
        }
    }
}
