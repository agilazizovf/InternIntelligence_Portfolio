package com.intern.portfolio.service.impl;

import com.intern.portfolio.dao.entity.UserDetailsEntity;
import com.intern.portfolio.dao.entity.UserEntity;
import com.intern.portfolio.model.dto.request.UserDetailsRequest;
import com.intern.portfolio.model.dto.response.UserDetailsResponse;
import com.intern.portfolio.model.exception.AuthenticationException;
import com.intern.portfolio.model.exception.ResourceNotFoundException;
import com.intern.portfolio.dao.repository.UserDetailsRepository;
import com.intern.portfolio.dao.repository.UserRepository;
import com.intern.portfolio.service.UserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserDetailsRepository userDetailsRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public ResponseEntity<UserDetailsResponse> create(UserDetailsRequest request) {
        UserEntity user = getCurrentUser();

        if (userDetailsRepository.existsByUserId(user.getId())) {
            throw new IllegalStateException("User has already added their details.");
        }
        UserDetailsEntity userDetails = new UserDetailsEntity();
        modelMapper.map(request, userDetails);
        userDetails.setUser(user);
        userDetailsRepository.save(userDetails);

        System.out.println("Created UserDetails: " + userDetails.toString());

        UserDetailsResponse response = new UserDetailsResponse();
        modelMapper.map(userDetails, response);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<UserDetailsResponse> update(Long id, UserDetailsRequest request) {
        UserEntity user = getCurrentUser();

        UserDetailsEntity userDetails = userDetailsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User details not found!"));

        if (!userDetails.getUser().equals(user)) {
            throw new AuthenticationException("You do not have permission to update these user details");
        }
        modelMapper.map(request, userDetails);
        userDetails.setUser(user);
        userDetailsRepository.save(userDetails);

        System.out.println("Updated UserDetails: " + userDetails.toString());

        UserDetailsResponse response = new UserDetailsResponse();
        modelMapper.map(userDetails, response);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<UserDetailsResponse> getById(Long id) {
        UserEntity user = getCurrentUser();

        UserDetailsEntity userDetails = userDetailsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User details not found!"));

        if (!userDetails.getUser().equals(user)) {
            throw new AuthenticationException("You do not have permission to access these user details");
        }

        UserDetailsResponse response = modelMapper.map(userDetails, UserDetailsResponse.class);

        log.info("Fetched user details: {} for user: {}", userDetails.getId(), user.getUsername());

        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }

    @Override
    public ResponseEntity<List<UserDetailsResponse>> getAll() {
        UserEntity user = getCurrentUser();

        List<UserDetailsEntity> userDetailsList = userDetailsRepository.findAllUserDetailsByUserId(user.getId());

        List<UserDetailsResponse> responses = userDetailsList.stream()
                .map(userDetails -> modelMapper.map(userDetails, UserDetailsResponse.class))
                .toList();

        log.info("Fetched all user details, total count: {}", responses.size());

        return ResponseEntity.ok(responses);
    }


    @Override
    public void delete(Long id) {
        UserEntity user = getCurrentUser();

        UserDetailsEntity userDetails = userDetailsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User details not found!"));

        if (!userDetails.getUser().equals(user)) {
            throw new AuthenticationException("You do not have permission to delete these user details");
        }
        userDetailsRepository.deleteById(id);

        System.out.println("Deleted UserDetails: " + userDetails.toString());
    }

    @Override
    public UserEntity getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
