package com.intern.portfolio.controller;

import com.intern.portfolio.model.dto.request.CertificateRequest;
import com.intern.portfolio.model.dto.response.CertificateResponse;
import com.intern.portfolio.service.CertificateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certificates")
@RequiredArgsConstructor
public class CertificateController {

    private final CertificateService certificateService;

    @PostMapping("/create")
    public ResponseEntity<CertificateResponse> createCertificate(@RequestBody @Valid CertificateRequest request) {
        return certificateService.create(request);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CertificateResponse> updateCertificate(
            @PathVariable Long id,
            @RequestBody @Valid CertificateRequest request) {
        return certificateService.update(id, request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CertificateResponse> getCertificateById(@PathVariable Long id) {
        return certificateService.getById(id);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<CertificateResponse>> getAllCertificates() {
        return certificateService.getAll();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCertificate(@PathVariable Long id) {
        certificateService.delete(id);
    }
}
