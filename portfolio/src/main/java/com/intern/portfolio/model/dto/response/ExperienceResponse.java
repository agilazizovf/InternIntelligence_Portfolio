package com.intern.portfolio.model.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ExperienceResponse {

    private Long id;
    private String title;
    private String companyName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
}
