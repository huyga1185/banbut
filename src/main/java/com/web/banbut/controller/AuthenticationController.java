package com.web.banbut.controller;

import com.web.banbut.dto.request.AuthenticationRequest;
import com.web.banbut.dto.request.IntrospectRequest;
import com.web.banbut.dto.request.UpdateEmailRequest;
import com.web.banbut.dto.request.VerifyOTPRequest;
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

    @PostMapping("/otp/reset-password")
    public ApiResponse<String> requestOTPForResetPassoword(@RequestBody String email) {
        authenticationService.requestOTP(email, 0);
        return new ApiResponse<String>(
                "success", null
        );
    }

    @PostMapping("/otp/register")
    public ApiResponse<String> requestOTPForRegister(@RequestBody String email) {
        authenticationService.requestOTP(email, 1);
        return new ApiResponse<String>("success", null);
    }

    @PostMapping("/otp/verify")
    public ApiResponse<Map<String, Object>> verifyOtp(@RequestBody VerifyOTPRequest verifyOTPRequest) {
        return  new ApiResponse<>(
                "success",
                Map.of(
                    "result: ", authenticationService.verifyOTP(verifyOTPRequest)
                )
        );
    }

    @PostMapping("/otp/update-email/old")
    public ApiResponse<String> requestOTPForUpdateOldEmail(@RequestBody UpdateEmailRequest updateEmailRequest) {
        authenticationService.requestOTP(updateEmailRequest.getOldEmail(), 2);
        return  new ApiResponse<String>(
            "success",
            null
        );
    }

    @PostMapping("/otp/update-email/new")
    public ApiResponse<String> requestOTPForUpdateNewEmail(@RequestBody UpdateEmailRequest updateEmailRequest) {
        authenticationService.requestOTP(updateEmailRequest.getNewEmail(), 2);
        return  new ApiResponse<String>(
                "success",
                null
        );
    }
}
