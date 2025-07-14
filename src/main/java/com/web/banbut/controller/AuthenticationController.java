package com.web.banbut.controller;

import com.web.banbut.dto.request.AuthenticationRequest;
import com.web.banbut.dto.request.IntrospectRequest;
import com.web.banbut.dto.response.ApiResponse;
import com.web.banbut.service.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ApiResponse<Map<String, Object>> logIn(@RequestBody AuthenticationRequest authenticationRequest) {
        return new ApiResponse<>("success", Map.of("token", authenticationService.logIn(authenticationRequest)));
    }

    @PostMapping("/introspect")
    public ApiResponse<Map<String, Object>> introspect(@RequestBody IntrospectRequest introspectRequest) {
        return new ApiResponse<>(
                "success",
                Map.of(
                        "details", authenticationService.introspect(introspectRequest)
                )
        );
    }
}
