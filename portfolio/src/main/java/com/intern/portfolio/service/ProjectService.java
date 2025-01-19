package com.intern.portfolio.service;

import com.intern.portfolio.dao.entity.UserEntity;
import com.intern.portfolio.model.dto.request.ProjectRequest;
import com.intern.portfolio.model.dto.response.ProjectResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProjectService {

    ResponseEntity<ProjectResponse> create(ProjectRequest request);
    ResponseEntity<ProjectResponse> update(Long id, ProjectRequest request);
    ResponseEntity<ProjectResponse> getById(Long id);
    ResponseEntity<List<ProjectResponse>> getAll();
    void delete(Long id);
    UserEntity getCurrentUser();
}
