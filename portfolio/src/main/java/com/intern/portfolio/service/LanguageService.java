package com.intern.portfolio.service;

import com.intern.portfolio.model.dto.request.LanguageRequest;
import com.intern.portfolio.model.dto.response.LanguageResponse;
import com.intern.portfolio.dao.entity.UserEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface LanguageService {

    ResponseEntity<LanguageResponse> create(LanguageRequest request);
    ResponseEntity<LanguageResponse> update(Long id, LanguageRequest request);
    ResponseEntity<LanguageResponse> getById(Long id);
    ResponseEntity<List<LanguageResponse>> getAll();
    void delete(Long id);
    UserEntity getCurrentUser();
}
