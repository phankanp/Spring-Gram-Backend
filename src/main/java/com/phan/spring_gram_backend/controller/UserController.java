package com.phan.spring_gram_backend.controller;

import com.phan.spring_gram_backend.model.Followers;
import com.phan.spring_gram_backend.model.User;
import com.phan.spring_gram_backend.repository.FollowersRepository;
import com.phan.spring_gram_backend.repository.UserRepository;
import com.phan.spring_gram_backend.security.JwtProvider;
import com.phan.spring_gram_backend.security.payload.JWTResponse;
import com.phan.spring_gram_backend.security.payload.LoginRequest;
import com.phan.spring_gram_backend.service.UserDetailsServiceImpl;
import com.phan.spring_gram_backend.service.UserService;
import com.phan.spring_gram_backend.service.ValidationErrorService;
import com.phan.spring_gram_backend.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

import static com.phan.spring_gram_backend.security.SecurityConstants.TOKEN_PREFIX;

//import org.springframework.security.authentication.AuthenticationManager;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    @Autowired
    private ValidationErrorService validationErrorService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    FollowersRepository followersRepository;


    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {

        ResponseEntity<?> errors = validationErrorService.MapValidationService(result);
        if (errors != null) return errors;

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = TOKEN_PREFIX + jwtProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTResponse(true, jwt));
    }

    // User registration
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result, HttpServletRequest request) {

        userValidator.validate(user, result);

        ResponseEntity<?> errors = validationErrorService.MapValidationService(result);
        if (errors != null) return errors;

        User newUser = userService.saveUser(user);

        return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
    }

    // Load user
    @GetMapping("/user")
    public ResponseEntity<?> getUser(@AuthenticationPrincipal User user) {

        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(user);
    }

}
