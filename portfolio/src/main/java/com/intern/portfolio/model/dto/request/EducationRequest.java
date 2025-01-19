package com.intern.portfolio.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EducationRequest {

    @NotBlank(message = "Degree cannot be blank")
    private String degree;

    @NotBlank(message = "Institution cannot be blank")
    private String institution;

    @NotNull(message = "Start year is required")
    private Integer startYear;

    @NotNull(message = "End year is required")
    private Integer endYear;

    private String grade; // Optional
}
