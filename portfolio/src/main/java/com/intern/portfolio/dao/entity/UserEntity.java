package com.intern.portfolio.dao.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.intern.portfolio.dao.entity.AuthorityEntity;
import com.intern.portfolio.dao.entity.UserDetailsEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @JsonIgnore
    private String password;

    @JsonBackReference
    @OneToOne(mappedBy = "user")
    private UserDetailsEntity userDetails;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<ProjectEntity> projects;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<SkillEntity> skills;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<EducationEntity> educations;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<ExperienceEntity> experiences;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<SocialMediaEntity> socialMedias;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<CertificateEntity> certificates;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<LanguageEntity> languages;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "user_authorities",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "name")})
    private Set<AuthorityEntity> authorities = new HashSet<>();

    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }

}
