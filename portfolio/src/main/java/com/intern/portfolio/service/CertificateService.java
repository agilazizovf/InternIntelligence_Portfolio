package com.intern.portfolio.service;

import com.intern.portfolio.dao.entity.UserEntity;
import com.intern.portfolio.model.dto.request.CertificateRequest;
import com.intern.portfolio.model.dto.response.CertificateResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CertificateService {

    ResponseEntity<CertificateResponse> create(CertificateRequest request);
    ResponseEntity<CertificateResponse> update(Long id, CertificateRequest request);
    ResponseEntity<CertificateResponse> getById(Long id);
    ResponseEntity<List<CertificateResponse>> getAll();
    void delete(Long id);
    UserEntity getCurrentUser();
}
