package com.intern.portfolio.service;

import com.intern.portfolio.dao.entity.UserEntity;
import com.intern.portfolio.model.dto.request.UserDetailsRequest;
import com.intern.portfolio.model.dto.response.UserDetailsResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserDetailsService {

    ResponseEntity<UserDetailsResponse> create(UserDetailsRequest request);
    ResponseEntity<UserDetailsResponse> update(Long id, UserDetailsRequest request);
    ResponseEntity<UserDetailsResponse> getById(Long id);
    ResponseEntity<List<UserDetailsResponse>> getAll();
    void delete(Long id);
    UserEntity getCurrentUser();
}
