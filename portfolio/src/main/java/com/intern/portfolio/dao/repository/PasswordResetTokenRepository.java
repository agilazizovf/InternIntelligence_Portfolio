package com.intern.portfolio.dao.repository;

import com.intern.portfolio.dao.entity.PasswordResetTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetTokenEntity, Long> {

    Optional<PasswordResetTokenEntity> findByToken(String token);
    void deleteByUser_Username(String username);
    void deleteByToken(String token);
}
