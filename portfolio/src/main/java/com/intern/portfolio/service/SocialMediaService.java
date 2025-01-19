package com.intern.portfolio.service;

import com.intern.portfolio.dao.entity.UserEntity;
import com.intern.portfolio.model.dto.request.SocialMediaRequest;
import com.intern.portfolio.model.dto.response.SocialMediaResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SocialMediaService {

    ResponseEntity<SocialMediaResponse> create(SocialMediaRequest request);
    ResponseEntity<SocialMediaResponse> update(Long id, SocialMediaRequest request);
    ResponseEntity<SocialMediaResponse> getById(Long id);
    ResponseEntity<List<SocialMediaResponse>> getAll();
    void delete(Long id);
    UserEntity getCurrentUser();
}
