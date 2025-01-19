package com.intern.portfolio.service;

import com.intern.portfolio.model.dto.request.LoginRequest;
import org.springframework.http.ResponseEntity;

public interface LoginService {

    ResponseEntity<?> login(LoginRequest request);
}
