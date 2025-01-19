package com.intern.portfolio.model.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SkillRequest {

    @NotBlank(message = "Skill name is required.")
    @Size(max = 50, message = "Skill name must not exceed 50 characters.")
    private String name;

    @Size(max = 250, message = "Description must not exceed 250 characters.")
    private String description;

    @Min(value = 1, message = "Proficiency level must be at least 1.")
    @Max(value = 10, message = "Proficiency level must not exceed 10.")
    private int proficiencyLevel;
}
