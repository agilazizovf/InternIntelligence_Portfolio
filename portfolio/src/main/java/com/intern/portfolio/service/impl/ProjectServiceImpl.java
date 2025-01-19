package com.intern.portfolio.service.impl;

import com.intern.portfolio.dao.entity.ProjectEntity;
import com.intern.portfolio.dao.entity.UserEntity;
import com.intern.portfolio.dao.repository.ProjectRepository;
import com.intern.portfolio.dao.repository.UserRepository;
import com.intern.portfolio.model.dto.request.ProjectRequest;
import com.intern.portfolio.model.dto.response.ProjectResponse;
import com.intern.portfolio.model.dto.response.UserDetailsResponse;
import com.intern.portfolio.model.exception.AuthenticationException;
import com.intern.portfolio.model.exception.ResourceNotFoundException;
import com.intern.portfolio.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectServiceImpl implements ProjectService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ProjectRepository projectRepository;
    @Override
    public ResponseEntity<ProjectResponse> create(ProjectRequest request) {
        UserEntity user = getCurrentUser();

        ProjectEntity project = new ProjectEntity();
        modelMapper.map(request, project);
        project.setUser(user);
        projectRepository.save(project);

        log.info("project: {} added successfully", project.getTitle());

        ProjectResponse response = new ProjectResponse();
        modelMapper.map(project, response);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<ProjectResponse> update(Long id, ProjectRequest request) {
        UserEntity user = getCurrentUser();

        ProjectEntity project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found!"));

        if (!project.getUser().equals(user)) {
            throw new AuthenticationException("You do not have permission to update this project!");
        }
        modelMapper.map(request, project);
        projectRepository.save(project);

        ProjectResponse response = new ProjectResponse();
        modelMapper.map(project, response);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ProjectResponse> getById(Long id) {
        UserEntity user = getCurrentUser();

        ProjectEntity project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found!"));

        if (!project.getUser().equals(user)) {
            throw new AuthenticationException("You do not have permission to access this project!");
        }

        ProjectResponse response = modelMapper.map(project, ProjectResponse.class);

        log.info("Fetched project details: {} for user: {}", project.getTitle(), user.getUsername());

        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }

    @Override
    public ResponseEntity<List<ProjectResponse>> getAll() {
        List<ProjectEntity> projectEntityList = projectRepository.findAll();

        List<ProjectResponse> responses = projectEntityList.stream()
                .map(projectEntity -> modelMapper.map(projectEntity, ProjectResponse.class))
                .toList();
        log.info("Fetched all projects, total count: {}", responses.size());

        return ResponseEntity.ok(responses);
    }

    @Override
    public void delete(Long id) {
        UserEntity user = getCurrentUser();

        ProjectEntity project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found!"));

        if (!project.getUser().equals(user)) {
            throw new AuthenticationException("You do not have permission to delete this project!");
        }
        projectRepository.deleteById(id);

    }

    @Override
    public UserEntity getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
