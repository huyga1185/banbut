package com.web.banbut.service;

import com.web.banbut.dto.request.AuthenticationRequest;
import com.web.banbut.dto.response.AuthenticationResponse;
import com.web.banbut.entity.User;
import com.web.banbut.exception.AppException;
import com.web.banbut.exception.ErrorCode;
import com.web.banbut.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AuthenticationResponse logIn(AuthenticationRequest authenticationRequest) {
        User user = userRepository.findByUsername(authenticationRequest.getUsername()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        if (!user.getPassword().equals(authenticationRequest.getPassword()))
            throw new AppException(ErrorCode.PASSWORD_DOES_NOT_MATCH);
        return new AuthenticationResponse("x");
    }
}
