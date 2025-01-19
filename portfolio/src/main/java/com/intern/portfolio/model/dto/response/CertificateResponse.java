package com.intern.portfolio.model.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CertificateResponse {

    private Long id;
    private String name;
    private String institution;
    private LocalDate issueDate;
    private String certificateUrl;
}
