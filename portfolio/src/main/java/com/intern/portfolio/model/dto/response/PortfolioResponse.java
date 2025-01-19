package com.intern.portfolio.model.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class PortfolioResponse {

    private Long userId;
    private List<UserDetailsResponse> userDetails;
    private List<ExperienceResponse> experiences;
    private List<CertificateResponse> certificates;
    private List<ProjectResponse> projects;
    private List<SkillResponse> skills;
    private List<LanguageResponse> languages;
    private List<SocialMediaResponse> socialMedias;
    private List<EducationResponse> educations;
}
