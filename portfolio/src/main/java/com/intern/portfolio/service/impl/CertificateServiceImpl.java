package com.intern.portfolio.service.impl;

import com.intern.portfolio.dao.entity.CertificateEntity;
import com.intern.portfolio.dao.entity.UserEntity;
import com.intern.portfolio.dao.repository.CertificateRepository;
import com.intern.portfolio.dao.repository.UserRepository;
import com.intern.portfolio.model.dto.request.CertificateRequest;
import com.intern.portfolio.model.dto.response.CertificateResponse;
import com.intern.portfolio.model.exception.AuthenticationException;
import com.intern.portfolio.model.exception.ResourceNotFoundException;
import com.intern.portfolio.service.CertificateService;
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
public class CertificateServiceImpl implements CertificateService {

    private final UserRepository userRepository;
    private final CertificateRepository certificateRepository;
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<CertificateResponse> create(CertificateRequest request) {
        UserEntity user = getCurrentUser();

        CertificateEntity certificate = modelMapper.map(request, CertificateEntity.class);
        certificate.setUser(user);
        certificateRepository.save(certificate);

        log.info("Certificate {} added successfully for user {}", certificate.getName(), user.getUsername());

        CertificateResponse response = modelMapper.map(certificate, CertificateResponse.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<CertificateResponse> update(Long id, CertificateRequest request) {
        UserEntity user = getCurrentUser();

        CertificateEntity certificate = certificateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Certificate not found!"));

        if (!certificate.getUser().equals(user)) {
            throw new AuthenticationException("You do not have permission to update this certificate!");
        }

        modelMapper.map(request, certificate);
        certificateRepository.save(certificate);

        CertificateResponse response = modelMapper.map(certificate, CertificateResponse.class);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<CertificateResponse> getById(Long id) {
        UserEntity user = getCurrentUser();

        CertificateEntity certificate = certificateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Certificate not found!"));

        if (!certificate.getUser().equals(user)) {
            throw new AuthenticationException("You do not have permission to access this certificate!");
        }

        CertificateResponse response = modelMapper.map(certificate, CertificateResponse.class);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<CertificateResponse>> getAll() {
        UserEntity user = getCurrentUser();

        List<CertificateEntity> certificateList = certificateRepository.findAllCertificatesByUserId(user.getId());

        List<CertificateResponse> responses = certificateList.stream()
                .map(certificate -> modelMapper.map(certificate, CertificateResponse.class))
                .toList();

        log.info("Fetched all certificates for user: {}, total count: {}", user.getUsername(), responses.size());

        return ResponseEntity.ok(responses);
    }

    @Override
    public void delete(Long id) {
        UserEntity user = getCurrentUser();

        CertificateEntity certificate = certificateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Certificate not found!"));

        if (!certificate.getUser().equals(user)) {
            throw new AuthenticationException("You do not have permission to delete this certificate!");
        }

        certificateRepository.deleteById(id);
        log.info("Certificate {} deleted successfully", certificate.getName());
    }

    @Override
    public UserEntity getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }
}
