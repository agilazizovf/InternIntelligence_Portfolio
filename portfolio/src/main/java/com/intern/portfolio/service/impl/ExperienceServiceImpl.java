package com.intern.portfolio.service.impl;

import com.intern.portfolio.dao.entity.ExperienceEntity;
import com.intern.portfolio.dao.entity.UserEntity;
import com.intern.portfolio.dao.repository.ExperienceRepository;
import com.intern.portfolio.dao.repository.UserRepository;
import com.intern.portfolio.model.dto.request.ExperienceRequest;
import com.intern.portfolio.model.dto.response.ExperienceResponse;
import com.intern.portfolio.model.exception.AuthenticationException;
import com.intern.portfolio.model.exception.ResourceNotFoundException;
import com.intern.portfolio.service.ExperienceService;
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
public class ExperienceServiceImpl implements ExperienceService {

    private final UserRepository userRepository;
    private final ExperienceRepository experienceRepository;
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<ExperienceResponse> create(ExperienceRequest request) {
        UserEntity user = getCurrentUser();

        ExperienceEntity experience = modelMapper.map(request, ExperienceEntity.class);
        experience.setUser(user);
        experienceRepository.save(experience);

        log.info("Experience {} at {} added successfully for user {}", experience.getTitle(), experience.getCompanyName(), user.getUsername());

        ExperienceResponse response = modelMapper.map(experience, ExperienceResponse.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<ExperienceResponse> update(Long id, ExperienceRequest request) {
        UserEntity user = getCurrentUser();

        ExperienceEntity experience = experienceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Experience not found!"));

        if (!experience.getUser().equals(user)) {
            throw new AuthenticationException("You do not have permission to update this experience!");
        }

        modelMapper.map(request, experience);
        experienceRepository.save(experience);

        ExperienceResponse response = modelMapper.map(experience, ExperienceResponse.class);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ExperienceResponse> getById(Long id) {
        UserEntity user = getCurrentUser();

        ExperienceEntity experience = experienceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Experience not found!"));

        if (!experience.getUser().equals(user)) {
            throw new AuthenticationException("You do not have permission to access this experience!");
        }

        ExperienceResponse response = modelMapper.map(experience, ExperienceResponse.class);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<ExperienceResponse>> getAll() {
        UserEntity user = getCurrentUser();

        List<ExperienceEntity> experienceList = experienceRepository.findAllExperiencesByUserId(user.getId());

        List<ExperienceResponse> responses = experienceList.stream()
                .map(experience -> modelMapper.map(experience, ExperienceResponse.class))
                .toList();

        log.info("Fetched all experiences for user: {}, total count: {}", user.getUsername(), responses.size());

        return ResponseEntity.ok(responses);
    }

    @Override
    public void delete(Long id) {
        UserEntity user = getCurrentUser();

        ExperienceEntity experience = experienceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Experience not found!"));

        if (!experience.getUser().equals(user)) {
            throw new AuthenticationException("You do not have permission to delete this experience!");
        }

        experienceRepository.deleteById(id);
        log.info("Experience {} deleted successfully", experience.getTitle());
    }

    @Override
    public UserEntity getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }
}
