package com.intern.portfolio.service;

import com.intern.portfolio.dao.entity.UserEntity;
import com.intern.portfolio.model.dto.request.EducationRequest;
import com.intern.portfolio.model.dto.response.EducationResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EducationService {

    ResponseEntity<EducationResponse> create(EducationRequest request);

    ResponseEntity<EducationResponse> update(Long id, EducationRequest request);

    ResponseEntity<EducationResponse> getById(Long id);

    ResponseEntity<List<EducationResponse>> getAll();

    void delete(Long id);

    UserEntity getCurrentUser();
}
