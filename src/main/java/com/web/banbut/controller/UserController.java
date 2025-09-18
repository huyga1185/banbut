package com.web.banbut.controller;

import com.web.banbut.dto.request.ResetPasswordRequest;
import com.web.banbut.dto.request.UserCreationRequest;
import com.web.banbut.dto.response.ApiResponse;
import com.web.banbut.exception.AppException;
import com.web.banbut.exception.ErrorCode;
import com.web.banbut.service.AuthenticationService;
import com.web.banbut.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    public UserController(
        UserService userService,
        AuthenticationService authenticationService
    ) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ApiResponse<Map<String, Object>> register(@RequestHeader("X-REQUEST-TOKEN") String token, @RequestBody UserCreationRequest userCreationRequest) {
        if (!authenticationService.verifyTemporaryToken(token, userCreationRequest.getEmail()))
            throw new AppException(ErrorCode.TOKEN_INVALID);
        return new ApiResponse<>("success", Map.of( "token",userService.register(userCreationRequest)));
    }

    @PostMapping("/reset-password")
    public ApiResponse<Map<String, String>> resetPassword(@RequestHeader("X-REQUEST-TOKEN") String token,  @RequestBody ResetPasswordRequest resetPasswordRequest) {
        if (!authenticationService.verifyTemporaryToken(token, resetPasswordRequest.getEmail()))
            throw new AppException(ErrorCode.TOKEN_INVALID);
        userService.resetPassword(resetPasswordRequest);
        return new ApiResponse<Map<String, String>>(
            "success",
            Map.of(
                "result", "Reset Password Success"
            )
        );
    }
}
