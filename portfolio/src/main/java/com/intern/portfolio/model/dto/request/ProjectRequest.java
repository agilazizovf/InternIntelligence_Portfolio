package com.intern.portfolio.model.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ProjectRequest {

    @NotEmpty(message = "Title is required")
    @Size(max = 255, message = "Title should not exceed 255 characters")
    private String title;

    @NotEmpty(message = "Description is required")
    private String description;

    @NotNull(message = "Technologies used cannot be null")
    @Size(min = 1, message = "At least one technology should be provided")
    private List<String> technologiesUsed;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    @NotEmpty(message = "Project URL is required")
    @Size(max = 255, message = "Project URL should not exceed 255 characters")
    private String projectUrl;
}
