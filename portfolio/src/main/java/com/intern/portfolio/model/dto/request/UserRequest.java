package com.intern.portfolio.model.dto.request;

import jakarta.validation.constraints.*;

import lombok.Data;

@Data
public class UserRequest {

    @NotBlank(message = "Username is required and cannot be blank.")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters.")
    private String username;

    @NotBlank(message = "Email is required and cannot be blank.")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "Email must be valid.Example: firstname-lastname@example.com  ")
    private String email;

    @NotBlank(message = "Password is required and cannot be blank.")
    @Size(min = 1, message = "Password must be at least 1 characters long.")
//    @Pattern(regexp = ".*\\d.*", message = "Password must contain at least one number.")
//    @Pattern(regexp = ".*[A-Z].*", message = "Password must contain at least one uppercase letter.")
//    @Pattern(regexp = ".*[@#$%^&+=!].*", message = "Password must contain at least one special character (@#$%^&+=!).")
    private String password;
}
