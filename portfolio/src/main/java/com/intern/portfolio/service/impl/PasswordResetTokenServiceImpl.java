package com.intern.portfolio.service.impl;

import com.intern.portfolio.dao.entity.PasswordResetTokenEntity;
import com.intern.portfolio.dao.entity.UserEntity;
import com.intern.portfolio.dao.repository.PasswordResetTokenRepository;
import com.intern.portfolio.dao.repository.UserRepository;
import com.intern.portfolio.model.exception.AuthenticationException;
import com.intern.portfolio.model.exception.ResourceNotFoundException;
import com.intern.portfolio.service.PasswordResetTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.InvalidParameterException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {
    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final JavaMailSender mailSender;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void initiatePasswordReset(String email) {
        UserEntity currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        Optional<UserEntity> user = userRepository.findByUsername(currentUser.getUsername());
        if (user.isEmpty()) {
            throw new AuthenticationException("User not associated with client: " + email);
        }

        // Delete any existing token for the user
        tokenRepository.deleteByUser_Username(user.get().getUsername());

        // Generate and save a new token with expiration date
        String token = generateAndSaveActivationToken(currentUser.getEmail());

        // Send the reset email
        sendResetEmail(currentUser.getEmail(), token);
    }

    private void sendResetEmail(String email, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset Request");
        message.setText("To reset your password, take this token:\n" +token);

        mailSender.send(message);
    }

    @Override
    @Transactional
    public void resetPassword(String token, String newPassword) {
        PasswordResetTokenEntity resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new InvalidParameterException("Invalid token"));

        if (resetToken.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new InvalidParameterException("Token has expired");
        }

        UserEntity user = userRepository.findByUsername(resetToken.getUser().getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        // Optionally, delete the token after use
        tokenRepository.deleteByToken(token);
    }


    private String generateAndSaveActivationToken(String email) {
        String generatedToken = generateActivationCode();
        PasswordResetTokenEntity token = PasswordResetTokenEntity.builder()
                .token(generatedToken)
                .expirationDate(LocalDateTime.now().plusMinutes(15))  // Set expiration date here
                .user(userRepository.findByEmail(email)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found!")))
                .build();
        tokenRepository.save(token);
        return generatedToken;
    }

    private String generateActivationCode() {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < 6; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }
}
