package com.intern.portfolio.service.impl;

import com.intern.portfolio.dao.entity.SkillEntity;
import com.intern.portfolio.dao.entity.UserEntity;
import com.intern.portfolio.dao.repository.SkillRepository;
import com.intern.portfolio.dao.repository.UserRepository;
import com.intern.portfolio.model.dto.request.SkillRequest;
import com.intern.portfolio.model.dto.response.SkillResponse;
import com.intern.portfolio.model.exception.AuthenticationException;
import com.intern.portfolio.model.exception.ResourceNotFoundException;
import com.intern.portfolio.service.SkillService;
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
public class SkillServiceImpl implements SkillService {

    private final UserRepository userRepository;
    private final SkillRepository skillRepository;
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<SkillResponse> create(SkillRequest request) {
        UserEntity user = getCurrentUser();

        SkillEntity skill = modelMapper.map(request, SkillEntity.class);
        skill.setUser(user);
        skillRepository.save(skill);

        log.info("Skill {} added successfully for user {}", skill.getName(), user.getUsername());

        SkillResponse response = modelMapper.map(skill, SkillResponse.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<SkillResponse> update(Long id, SkillRequest request) {
        UserEntity user = getCurrentUser();

        SkillEntity skill = skillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found!"));

        if (!skill.getUser().equals(user)) {
            throw new AuthenticationException("You do not have permission to update this skill!");
        }

        modelMapper.map(request, skill);
        skillRepository.save(skill);

        SkillResponse response = modelMapper.map(skill, SkillResponse.class);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<SkillResponse> getById(Long id) {
        UserEntity user = getCurrentUser();

        SkillEntity skill = skillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found!"));

        if (!skill.getUser().equals(user)) {
            throw new AuthenticationException("You do not have permission to access this skill!");
        }

        SkillResponse response = modelMapper.map(skill, SkillResponse.class);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<SkillResponse>> getAll() {
        UserEntity user = getCurrentUser();

        List<SkillEntity> skillList = skillRepository.findAllSkillsByUserId(user.getId());

        List<SkillResponse> responses = skillList.stream()
                .map(skill -> modelMapper.map(skill, SkillResponse.class))
                .toList();

        log.info("Fetched all skills for user: {}, total count: {}", user.getUsername(), responses.size());

        return ResponseEntity.ok(responses);
    }


    @Override
    public void delete(Long id) {
        UserEntity user = getCurrentUser();

        SkillEntity skill = skillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found!"));

        if (!skill.getUser().equals(user)) {
            throw new AuthenticationException("You do not have permission to delete this skill!");
        }

        skillRepository.deleteById(id);
        log.info("Skill {} deleted successfully", skill.getName());
    }

    @Override
    public UserEntity getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }
}
