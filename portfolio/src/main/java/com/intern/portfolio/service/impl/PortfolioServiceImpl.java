package com.intern.portfolio.service.impl;

import com.intern.portfolio.dao.entity.UserEntity;
import com.intern.portfolio.dao.repository.UserRepository;
import com.intern.portfolio.model.dto.response.*;
import com.intern.portfolio.model.exception.ResourceNotFoundException;
import com.intern.portfolio.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {

    private final UserRepository userRepository;
    private final ExperienceService experienceService;
    private final CertificateService certificateService;
    private final ProjectService projectService;
    private final SkillService skillService;
    private final LanguageService languageService;
    private final SocialMediaService socialMediaService;
    private final EducationService educationService;
    private final UserDetailsService userDetailsService;

    @Override
    public ResponseEntity<PortfolioResponse> getUserPortfolio() {
        UserEntity user = getCurrentUser();

        List<UserDetailsResponse> userDetails = userDetailsService.getAll().getBody();
        List<ExperienceResponse> experiences = experienceService.getAll().getBody();
        List<EducationResponse> educations = educationService.getAll().getBody();
        List<CertificateResponse> certificates = certificateService.getAll().getBody();
        List<ProjectResponse> projects = projectService.getAll().getBody();
        List<SkillResponse> skills = skillService.getAll().getBody();
        List<LanguageResponse> languages = languageService.getAll().getBody();
        List<SocialMediaResponse> socialMedias = socialMediaService.getAll().getBody();

        PortfolioResponse portfolioResponse = new PortfolioResponse();
        portfolioResponse.setUserId(user.getId());
        portfolioResponse.setUserDetails(userDetails);
        portfolioResponse.setEducations(educations);
        portfolioResponse.setExperiences(experiences);
        portfolioResponse.setCertificates(certificates);
        portfolioResponse.setProjects(projects);
        portfolioResponse.setSkills(skills);
        portfolioResponse.setLanguages(languages);
        portfolioResponse.setSocialMedias(socialMedias);

        return ResponseEntity.ok(portfolioResponse);
    }

    @Override
    public UserEntity getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }
}
