package com.intern.portfolio.service.impl;

import com.intern.portfolio.dao.entity.AuthorityEntity;
import com.intern.portfolio.dao.entity.UserEntity;
import com.intern.portfolio.model.dto.request.UserRequest;
import com.intern.portfolio.model.dto.response.UserResponse;
import com.intern.portfolio.model.exception.AlreadyExistsException;
import com.intern.portfolio.dao.repository.UserRepository;
import com.intern.portfolio.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    @Override
    public ResponseEntity<UserResponse> register(UserRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new AlreadyExistsException("User already exists!");
        }
        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        AuthorityEntity authorityEntity = new AuthorityEntity("USER");
        Set<AuthorityEntity> authorityEntitySet = Set.of(authorityEntity);
        user.setAuthorities(authorityEntitySet);

        userRepository.save(user);

        log.info("user: {} registered successfully", user.getUsername());

        UserResponse response = new UserResponse();
        modelMapper.map(user, response);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
