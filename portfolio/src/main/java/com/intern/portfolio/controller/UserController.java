package com.intern.portfolio.controller;

import com.intern.portfolio.model.dto.request.LoginRequest;
import com.intern.portfolio.model.dto.request.UserRequest;
import com.intern.portfolio.model.dto.response.UserResponse;
import com.intern.portfolio.service.LoginService;
import com.intern.portfolio.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final LoginService loginService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid UserRequest request) {
        return userService.register(request);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) {
        return loginService.login(request);
    }
}
