package com.intern.portfolio.controller;

import com.intern.portfolio.model.dto.request.ExperienceRequest;
import com.intern.portfolio.model.dto.response.ExperienceResponse;
import com.intern.portfolio.service.ExperienceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/experiences")
@RequiredArgsConstructor
public class ExperienceController {

    private final ExperienceService experienceService;

    @PostMapping("/create")
    public ResponseEntity<ExperienceResponse> createExperience(@RequestBody @Valid ExperienceRequest request) {
        return experienceService.create(request);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ExperienceResponse> updateExperience(
            @PathVariable Long id,
            @RequestBody @Valid ExperienceRequest request) {
        return experienceService.update(id, request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExperienceResponse> getExperienceById(@PathVariable Long id) {
        return experienceService.getById(id);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<ExperienceResponse>> getAllExperiences() {
        return experienceService.getAll();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteExperience(@PathVariable Long id) {
        experienceService.delete(id);
    }
}
