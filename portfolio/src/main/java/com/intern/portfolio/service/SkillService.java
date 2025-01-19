package com.intern.portfolio.service;

import com.intern.portfolio.dao.entity.UserEntity;
import com.intern.portfolio.model.dto.request.SkillRequest;
import com.intern.portfolio.model.dto.response.SkillResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SkillService {

    ResponseEntity<SkillResponse> create(SkillRequest request);
    ResponseEntity<SkillResponse> update(Long id, SkillRequest request);
    ResponseEntity<SkillResponse> getById(Long id);
    ResponseEntity<List<SkillResponse>> getAll();
    void delete(Long id);
    UserEntity getCurrentUser();
}
