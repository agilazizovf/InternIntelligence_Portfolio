package com.intern.portfolio.service;

import com.intern.portfolio.dao.entity.UserEntity;
import com.intern.portfolio.model.dto.request.ExperienceRequest;
import com.intern.portfolio.model.dto.response.ExperienceResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ExperienceService {

    ResponseEntity<ExperienceResponse> create(ExperienceRequest request);

    ResponseEntity<ExperienceResponse> update(Long id, ExperienceRequest request);

    ResponseEntity<ExperienceResponse> getById(Long id);

    ResponseEntity<List<ExperienceResponse>> getAll();

    void delete(Long id);

    UserEntity getCurrentUser();
}
