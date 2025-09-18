package com.web.banbut.controller;

import com.web.banbut.dto.request.AuthenticationRequest;
import com.web.banbut.dto.request.IntrospectRequest;
import com.web.banbut.dto.response.ApiResponse;
import com.web.banbut.service.AuthenticationService;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/{email}")
    public ApiResponse<String> requestOtp(@PathVariable String email) {
        authenticationService.requestOTP(email);
        return new ApiResponse<String>(
                "success", null
        );
    }

    @PostMapping("/{email}/{otp}")
    public ApiResponse<Map<String, Object>> verifyOtp(@PathVariable String email, @PathVariable String otp) {
        return  new ApiResponse<>(
                "success",
                Map.of(
                        "result: ", authenticationService.verifyOTP(email, otp)
                )
        );
    }
}
