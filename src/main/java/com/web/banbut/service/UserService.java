package com.web.banbut.service;

import com.web.banbut.dto.request.AuthenticationRequest;
import com.web.banbut.dto.request.UserCreationRequest;
import com.web.banbut.dto.response.AuthenticationResponse;
import com.web.banbut.entity.User;
import com.web.banbut.exception.AppException;
import com.web.banbut.exception.ErrorCode;
import com.web.banbut.repository.UserRepository;
import org.springframework.stereotype.Service;

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
        User user = new User(
                userCreationRequest.getUsername(),
                userCreationRequest.getPassword(),
                userCreationRequest.getEmail()
        );
        userRepository.save(user);
        cartService.createCart(user);
        return authenticationService.logIn(new AuthenticationRequest(userCreationRequest.getUsername(), userCreationRequest.getPassword()));
    }

}
