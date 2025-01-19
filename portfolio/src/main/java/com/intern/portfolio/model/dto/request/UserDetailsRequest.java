package com.intern.portfolio.model.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDetailsRequest {

    @NotBlank(message = "Name is required and cannot be blank.")
    @Size(max = 50, message = "Name must not exceed 50 characters.")
    private String name;

    @NotBlank(message = "Surname is required and cannot be blank.")
    @Size(max = 50, message = "Surname must not exceed 50 characters.")
    private String surname;

    @NotBlank(message = "Phone number is required and cannot be blank.")
    @Pattern(regexp = "^(\\+994|0)(50|51|55|70|77|99)\\d{7}$", message = "Phone number must be valid. Example: +994000000000 or 0000000000")
    private String phone;

    @NotBlank(message = "Email is required and cannot be blank.")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "Email must be valid.Example: firstname-lastname@example.com  ")
    private String email;

    @NotNull(message = "Birthday is required.")
    @Past(message = "Birthday must be in the past.")
    private LocalDate birthday;

    @NotBlank(message = "Country is required and cannot be blank.")
    @Size(max = 50, message = "Country name must not exceed 50 characters.")
    private String country;

    @Size(max = 500, message = "Summary must not exceed 500 characters.")
    private String summary;

    @Min(value = 0, message = "Required minimum wage must be at least 0.")
    private int requiredMinimumWage;
}
