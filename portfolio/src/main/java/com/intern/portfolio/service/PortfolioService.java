package com.intern.portfolio.service;

import com.intern.portfolio.dao.entity.UserEntity;
import com.intern.portfolio.model.dto.response.PortfolioResponse;
import org.springframework.http.ResponseEntity;

public interface PortfolioService {

    ResponseEntity<PortfolioResponse> getUserPortfolio();
    UserEntity getCurrentUser();
}
