package com.intern.portfolio.model.dto.response;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ProjectResponse {

    private Long id;
    private String title;
    private String description;
    private List<String> technologiesUsed;
    private LocalDate startDate;
    private LocalDate endDate;
    private String projectUrl;
}
