package com.intern.portfolio.controller;

import com.intern.portfolio.model.dto.request.LanguageRequest;
import com.intern.portfolio.model.dto.response.LanguageResponse;
import com.intern.portfolio.service.LanguageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/languages")
@RequiredArgsConstructor
public class LanguageController {

    private final LanguageService languageService;

    @PostMapping("/create")
    public ResponseEntity<LanguageResponse> createLanguage(@RequestBody @Valid LanguageRequest request) {
        return languageService.create(request);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<LanguageResponse> updateLanguage(
            @PathVariable Long id,
            @RequestBody @Valid LanguageRequest request) {
        return languageService.update(id, request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LanguageResponse> getLanguageById(@PathVariable Long id) {
        return languageService.getById(id);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<LanguageResponse>> getAllLanguages() {
        return languageService.getAll();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteLanguage(@PathVariable Long id) {
        languageService.delete(id);
    }
}
