package com.intern.portfolio.controller;

import com.intern.portfolio.model.dto.response.PortfolioResponse;
import com.intern.portfolio.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/portfolio")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;

    @GetMapping
    public ResponseEntity<PortfolioResponse> getUserPortfolio() {
        return portfolioService.getUserPortfolio();
    }
}
