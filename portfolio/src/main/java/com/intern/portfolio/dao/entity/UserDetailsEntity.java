package com.intern.portfolio.dao.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "users_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private String phone;
    private String email;
    private LocalDate birthday;
    private String country;
    private String summary;
    private int requiredMinimumWage;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private UserEntity user;

    @Override
    public String toString() {
        return "UserDetailsEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", birthday=" + birthday +
                ", country='" + country + '\'' +
                ", summary='" + summary + '\'' +
                ", requiredMinimumWage=" + requiredMinimumWage +
                '}';
    }

}
