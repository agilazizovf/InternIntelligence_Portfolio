package com.intern.portfolio.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ExperienceRequest {

    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotBlank(message = "Company name cannot be blank")
    private String companyName;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    private LocalDate endDate; // Optional

    private String description; // Optional
}
