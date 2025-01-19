package com.intern.portfolio.controller;

import com.intern.portfolio.model.dto.request.ProjectRequest;
import com.intern.portfolio.model.dto.response.ProjectResponse;
import com.intern.portfolio.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("/create")
    public ResponseEntity<ProjectResponse> createProject(@RequestBody @Valid ProjectRequest request) {
        return projectService.create(request);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProjectResponse> updateProject(
            @PathVariable Long id,
            @RequestBody @Valid ProjectRequest request) {
        return projectService.update(id, request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable Long id) {
        return projectService.getById(id);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<ProjectResponse>> getAllProjects() {
        return projectService.getAll();
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
