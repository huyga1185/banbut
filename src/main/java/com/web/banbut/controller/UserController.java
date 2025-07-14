package com.web.banbut.controller;

import com.web.banbut.dto.request.UserCreationRequest;
import com.web.banbut.dto.response.ApiResponse;
import com.web.banbut.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ApiResponse<Map<String, Object>> register(@RequestBody UserCreationRequest userCreationRequest) {
        return new ApiResponse<>("success", Map.of( "token",userService.register(userCreationRequest)));
    }
}
