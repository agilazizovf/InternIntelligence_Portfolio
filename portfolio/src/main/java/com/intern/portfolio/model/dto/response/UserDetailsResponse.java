package com.intern.portfolio.model.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDetailsResponse {

    private Long id;
    private String name;
    private String surname;
    private String phone;
    private String email;
    private LocalDate birthday;
    private String country;
    private String summary;
    private int requiredMinimumWage;
}
