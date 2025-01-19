package com.intern.portfolio.service.impl;

import com.intern.portfolio.dao.entity.SocialMediaEntity;
import com.intern.portfolio.dao.entity.UserEntity;
import com.intern.portfolio.dao.repository.SocialMediaRepository;
import com.intern.portfolio.dao.repository.UserRepository;
import com.intern.portfolio.model.dto.request.SocialMediaRequest;
import com.intern.portfolio.model.dto.response.SocialMediaResponse;
import com.intern.portfolio.model.exception.AuthenticationException;
import com.intern.portfolio.model.exception.ResourceNotFoundException;
import com.intern.portfolio.service.SocialMediaService;
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
public class SocialMediaServiceImpl implements SocialMediaService {

    private final UserRepository userRepository;
    private final SocialMediaRepository socialMediaRepository;
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<SocialMediaResponse> create(SocialMediaRequest request) {
        UserEntity user = getCurrentUser();

        SocialMediaEntity socialMedia = modelMapper.map(request, SocialMediaEntity.class);
        socialMedia.setUser(user);
        socialMediaRepository.save(socialMedia);

        log.info("Social media profile {} added successfully for user {}", socialMedia.getPlatformName(), user.getUsername());

        SocialMediaResponse response = modelMapper.map(socialMedia, SocialMediaResponse.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<SocialMediaResponse> update(Long id, SocialMediaRequest request) {
        UserEntity user = getCurrentUser();

        SocialMediaEntity socialMedia = socialMediaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Social media profile not found!"));

        if (!socialMedia.getUser().equals(user)) {
            throw new AuthenticationException("You do not have permission to update this social media profile!");
        }

        modelMapper.map(request, socialMedia);
        socialMediaRepository.save(socialMedia);

        SocialMediaResponse response = modelMapper.map(socialMedia, SocialMediaResponse.class);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<SocialMediaResponse> getById(Long id) {
        UserEntity user = getCurrentUser();

        SocialMediaEntity socialMedia = socialMediaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Social media profile not found!"));

        if (!socialMedia.getUser().equals(user)) {
            throw new AuthenticationException("You do not have permission to access this social media profile!");
        }

        SocialMediaResponse response = modelMapper.map(socialMedia, SocialMediaResponse.class);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<SocialMediaResponse>> getAll() {
        UserEntity user = getCurrentUser();

        List<SocialMediaEntity> socialMediaList = socialMediaRepository.findAllSocialMediasByUserId(user.getId());

        List<SocialMediaResponse> responses = socialMediaList.stream()
                .map(socialMedia -> modelMapper.map(socialMedia, SocialMediaResponse.class))
                .toList();

        log.info("Fetched all social media profiles for user: {}, total count: {}", user.getUsername(), responses.size());

        return ResponseEntity.ok(responses);
    }

    @Override
    public void delete(Long id) {
        UserEntity user = getCurrentUser();

        SocialMediaEntity socialMedia = socialMediaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Social media profile not found!"));

        if (!socialMedia.getUser().equals(user)) {
            throw new AuthenticationException("You do not have permission to delete this social media profile!");
        }

        socialMediaRepository.deleteById(id);
        log.info("Social media profile {} deleted successfully", socialMedia.getPlatformName());
    }

    @Override
    public UserEntity getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }
}
