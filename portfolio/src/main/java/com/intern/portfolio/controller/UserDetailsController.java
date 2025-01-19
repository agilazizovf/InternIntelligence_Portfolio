package com.intern.portfolio.controller;

import com.intern.portfolio.model.dto.request.UserDetailsRequest;
import com.intern.portfolio.model.dto.response.UserDetailsResponse;
import com.intern.portfolio.service.UserDetailsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-details")
@RequiredArgsConstructor
public class UserDetailsController {

    private final UserDetailsService userDetailsService;

    @PostMapping("/create")
    public ResponseEntity<UserDetailsResponse> createUserDetails(@Valid @RequestBody UserDetailsRequest request) {
        return userDetailsService.create(request);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<UserDetailsResponse> updateUserDetails(@PathVariable Long id,
                                                                 @Valid @RequestBody UserDetailsRequest request) {
        return userDetailsService.update(id, request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailsResponse> getUserDetailsById(@PathVariable Long id) {
        return userDetailsService.getById(id);
    }
    @GetMapping("/get-all")
    public ResponseEntity<List<UserDetailsResponse>> getAllUserDetails() {
        return userDetailsService.getAll();
    }
    @DeleteMapping("/delete/{id}")
    public void deleteUserDetails(@PathVariable Long id) {
        userDetailsService.delete(id);
    }
}
