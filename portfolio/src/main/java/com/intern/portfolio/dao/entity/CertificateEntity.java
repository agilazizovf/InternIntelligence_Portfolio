package com.intern.portfolio.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "certificates")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertificateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String institution;
    private LocalDate issueDate;
    private String certificateUrl;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
