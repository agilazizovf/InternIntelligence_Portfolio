package com.intern.portfolio.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "social_media")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocialMediaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String platformName;
    private String profileUrl;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
