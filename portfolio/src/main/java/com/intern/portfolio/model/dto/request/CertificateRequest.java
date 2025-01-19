package com.intern.portfolio.model.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CertificateRequest {

    private String name;
    private String institution;
    private LocalDate issueDate;
    private String certificateUrl;
}
