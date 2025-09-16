package com.web.banbut.service;

import com.web.banbut.dto.request.AuthenticationRequest;
import com.web.banbut.dto.request.UserCreationRequest;
import com.web.banbut.dto.response.AuthenticationResponse;
import com.web.banbut.entity.User;
import com.web.banbut.exception.AppException;
import com.web.banbut.exception.ErrorCode;
import com.web.banbut.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final AuthenticationService authenticationService;

    private final CartService cartService;

    public UserService(UserRepository userRepository, AuthenticationService authenticationService, CartService cartService) {
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
        this.cartService = cartService;
    }

    public AuthenticationResponse register(UserCreationRequest userCreationRequest) {
        if (userRepository.existsByUsername(userCreationRequest.getUsername()))
            throw new AppException(ErrorCode.USERNAME_EXISTED);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        User user = new User(
                userCreationRequest.getUsername(),
                passwordEncoder.encode(userCreationRequest.getPassword()),
                userCreationRequest.getEmail()
        );
        userRepository.save(user);
        cartService.createCart(user);
        return authenticationService.logIn(new AuthenticationRequest(userCreationRequest.getUsername(), userCreationRequest.getPassword()));
    }

    @Transactional
    public void resetPassword(String email, String newPassword) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
            );
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(newPassword));
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new AppException(ErrorCode.COULD_NOT_RESET_PASSWORD);
        }
    }
}
