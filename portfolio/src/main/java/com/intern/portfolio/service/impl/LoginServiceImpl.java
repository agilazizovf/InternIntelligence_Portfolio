package com.intern.portfolio.service.impl;

import com.intern.portfolio.dao.entity.UserEntity;
import com.intern.portfolio.model.ExceptionDTO;
import com.intern.portfolio.model.dto.request.LoginRequest;
import com.intern.portfolio.model.dto.response.LoginResponse;
import com.intern.portfolio.service.LoginService;
import com.intern.portfolio.utility.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginServiceImpl implements LoginService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    @Override
    public ResponseEntity<?> login(LoginRequest request) {
        log.info("authenticate method started by: {}", request.getUsername());
        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),
                            request.getPassword()));
            log.info("authentication details: {}", authentication);
            String username = authentication.getName();
            UserEntity client = new UserEntity(username,"");
            String token = jwtUtil.createToken(client);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
            LoginResponse loginRes = new LoginResponse(username,token);
            log.info("user: {} logged in",  client.getUsername());
            return ResponseEntity.status(HttpStatus.OK).headers(headers).body(loginRes);

        }catch (BadCredentialsException e){
            ExceptionDTO exceptionDTO = new ExceptionDTO(HttpStatus.BAD_REQUEST.value(),"Invalid username or password");
            log.error("Error due to {} ", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionDTO);
        }catch (Exception e){
            ExceptionDTO exceptionDTO = new ExceptionDTO(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            log.error("Error due to {} ", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionDTO);
        }
    }
}
