package com.intern.portfolio.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LanguageRequest {

    @NotNull(message = "Language name cannot be null")
    private String name;

    @NotNull(message = "Proficiency level cannot be null")
    private String proficiencyLevel;
}
