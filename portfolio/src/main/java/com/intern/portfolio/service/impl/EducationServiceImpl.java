package com.intern.portfolio.service.impl;

import com.intern.portfolio.dao.entity.EducationEntity;
import com.intern.portfolio.dao.entity.UserEntity;
import com.intern.portfolio.dao.repository.EducationRepository;
import com.intern.portfolio.dao.repository.UserRepository;
import com.intern.portfolio.model.dto.request.EducationRequest;
import com.intern.portfolio.model.dto.response.EducationResponse;
import com.intern.portfolio.model.exception.AuthenticationException;
import com.intern.portfolio.model.exception.ResourceNotFoundException;
import com.intern.portfolio.service.EducationService;
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
public class EducationServiceImpl implements EducationService {

    private final UserRepository userRepository;
    private final EducationRepository educationRepository;
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<EducationResponse> create(EducationRequest request) {
        UserEntity user = getCurrentUser();

        EducationEntity education = modelMapper.map(request, EducationEntity.class);
        education.setUser(user);
        educationRepository.save(education);

        log.info("Education {} added successfully for user {}", education.getDegree(), user.getUsername());

        EducationResponse response = modelMapper.map(education, EducationResponse.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<EducationResponse> update(Long id, EducationRequest request) {
        UserEntity user = getCurrentUser();

        EducationEntity education = educationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Education not found!"));

        if (!education.getUser().equals(user)) {
            throw new AuthenticationException("You do not have permission to update this education record!");
        }

        modelMapper.map(request, education);
        educationRepository.save(education);

        EducationResponse response = modelMapper.map(education, EducationResponse.class);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<EducationResponse> getById(Long id) {
        UserEntity user = getCurrentUser();

        EducationEntity education = educationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Education not found!"));

        if (!education.getUser().equals(user)) {
            throw new AuthenticationException("You do not have permission to access this education record!");
        }

        EducationResponse response = modelMapper.map(education, EducationResponse.class);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<EducationResponse>> getAll() {
        UserEntity user = getCurrentUser();

        List<EducationEntity> educationList = educationRepository.findAllEducationsByUserId(user.getId());

        List<EducationResponse> responses = educationList.stream()
                .map(education -> modelMapper.map(education, EducationResponse.class))
                .toList();

        log.info("Fetched all education records for user: {}, total count: {}", user.getUsername(), responses.size());

        return ResponseEntity.ok(responses);
    }

    @Override
    public void delete(Long id) {
        UserEntity user = getCurrentUser();

        EducationEntity education = educationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Education not found!"));

        if (!education.getUser().equals(user)) {
            throw new AuthenticationException("You do not have permission to delete this education record!");
        }

        educationRepository.deleteById(id);
        log.info("Education {} deleted successfully", education.getDegree());
    }

    @Override
    public UserEntity getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }
}
