package com.intern.portfolio.controller;

import com.intern.portfolio.model.dto.request.EducationRequest;
import com.intern.portfolio.model.dto.response.EducationResponse;
import com.intern.portfolio.service.EducationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/educations")
@RequiredArgsConstructor
public class EducationController {

    private final EducationService educationService;

    @PostMapping("/create")
    public ResponseEntity<EducationResponse> createEducation(@RequestBody @Valid EducationRequest request) {
        return educationService.create(request);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EducationResponse> updateEducation(
            @PathVariable Long id,
            @RequestBody @Valid EducationRequest request) {
        return educationService.update(id, request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EducationResponse> getEducationById(@PathVariable Long id) {
        return educationService.getById(id);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<EducationResponse>> getAllEducations() {
        return educationService.getAll();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteEducation(@PathVariable Long id) {
        educationService.delete(id);
    }
}
