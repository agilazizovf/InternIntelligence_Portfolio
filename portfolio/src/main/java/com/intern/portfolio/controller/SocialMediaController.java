package com.intern.portfolio.controller;

import com.intern.portfolio.model.dto.request.SocialMediaRequest;
import com.intern.portfolio.model.dto.response.SocialMediaResponse;
import com.intern.portfolio.service.SocialMediaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/social-media")
@RequiredArgsConstructor
public class SocialMediaController {

    private final SocialMediaService socialMediaService;

    @PostMapping("/create")
    public ResponseEntity<SocialMediaResponse> createSocialMedia(@RequestBody @Valid SocialMediaRequest request) {
        return socialMediaService.create(request);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SocialMediaResponse> updateSocialMedia(
            @PathVariable Long id,
            @RequestBody @Valid SocialMediaRequest request) {
        return socialMediaService.update(id, request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SocialMediaResponse> getSocialMediaById(@PathVariable Long id) {
        return socialMediaService.getById(id);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<SocialMediaResponse>> getAllSocialMedia() {
        return socialMediaService.getAll();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteSocialMedia(@PathVariable Long id) {
        socialMediaService.delete(id);
    }
}
