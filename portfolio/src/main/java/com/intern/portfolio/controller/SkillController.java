package com.intern.portfolio.controller;

import com.intern.portfolio.model.dto.request.SkillRequest;
import com.intern.portfolio.model.dto.response.SkillResponse;
import com.intern.portfolio.service.SkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skills")
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;

    @PostMapping("/create")
    public ResponseEntity<SkillResponse> createSkill(@RequestBody @Valid SkillRequest request) {
        return skillService.create(request);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SkillResponse> updateSkill(
            @PathVariable Long id,
            @RequestBody @Valid SkillRequest request) {
        return skillService.update(id, request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SkillResponse> getSkillById(@PathVariable Long id) {
        return skillService.getById(id);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<SkillResponse>> getAllSkills() {
        return skillService.getAll();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
        skillService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
