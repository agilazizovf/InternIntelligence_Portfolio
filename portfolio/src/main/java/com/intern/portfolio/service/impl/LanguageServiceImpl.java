package com.intern.portfolio.service.impl;

import com.intern.portfolio.dao.entity.LanguageEntity;
import com.intern.portfolio.dao.entity.UserEntity;
import com.intern.portfolio.dao.repository.LanguageRepository;
import com.intern.portfolio.dao.repository.UserRepository;
import com.intern.portfolio.model.dto.request.LanguageRequest;
import com.intern.portfolio.model.dto.response.LanguageResponse;
import com.intern.portfolio.model.exception.AuthenticationException;
import com.intern.portfolio.model.exception.ResourceNotFoundException;
import com.intern.portfolio.service.LanguageService;
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
public class LanguageServiceImpl implements LanguageService {

    private final UserRepository userRepository;
    private final LanguageRepository languageRepository;
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<LanguageResponse> create(LanguageRequest request) {
        UserEntity user = getCurrentUser();

        LanguageEntity language = modelMapper.map(request, LanguageEntity.class);
        language.setUser(user);
        languageRepository.save(language);

        log.info("Language {} added successfully for user {}", language.getName(), user.getUsername());

        LanguageResponse response = modelMapper.map(language, LanguageResponse.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<LanguageResponse> update(Long id, LanguageRequest request) {
        UserEntity user = getCurrentUser();

        LanguageEntity language = languageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Language not found!"));

        if (!language.getUser().equals(user)) {
            throw new AuthenticationException("You do not have permission to update this language!");
        }

        modelMapper.map(request, language);
        languageRepository.save(language);

        LanguageResponse response = modelMapper.map(language, LanguageResponse.class);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<LanguageResponse> getById(Long id) {
        UserEntity user = getCurrentUser();

        LanguageEntity language = languageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Language not found!"));

        if (!language.getUser().equals(user)) {
            throw new AuthenticationException("You do not have permission to access this language!");
        }

        LanguageResponse response = modelMapper.map(language, LanguageResponse.class);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<LanguageResponse>> getAll() {
        UserEntity user = getCurrentUser();

        List<LanguageEntity> languageList = languageRepository.findAllLanguagesByUserId(user.getId());

        List<LanguageResponse> responses = languageList.stream()
                .map(language -> modelMapper.map(language, LanguageResponse.class))
                .toList();

        log.info("Fetched all languages for user: {}, total count: {}", user.getUsername(), responses.size());

        return ResponseEntity.ok(responses);
    }

    @Override
    public void delete(Long id) {
        UserEntity user = getCurrentUser();

        LanguageEntity language = languageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Language not found!"));

        if (!language.getUser().equals(user)) {
            throw new AuthenticationException("You do not have permission to delete this language!");
        }

        languageRepository.deleteById(id);
        log.info("Language {} deleted successfully", language.getName());
    }

    @Override
    public UserEntity getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }
}
