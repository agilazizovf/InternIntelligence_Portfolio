package com.intern.portfolio.dao.repository;

import com.intern.portfolio.dao.entity.ExperienceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExperienceRepository extends JpaRepository<ExperienceEntity, Long> {

    List<ExperienceEntity> findAllExperiencesByUserId(Long userId);
}
