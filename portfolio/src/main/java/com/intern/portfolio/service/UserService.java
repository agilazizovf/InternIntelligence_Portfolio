package com.intern.portfolio.service;

import com.intern.portfolio.model.dto.request.UserRequest;
import com.intern.portfolio.model.dto.response.UserResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<UserResponse> register(UserRequest request);
    // update, delete...
}
