package com.intern.portfolio.model.dto.response;

import lombok.Data;

@Data
public class EducationResponse {

    private Long id;
    private String degree;
    private String institution;
    private Integer startYear;
    private Integer endYear;
    private String grade;
}
