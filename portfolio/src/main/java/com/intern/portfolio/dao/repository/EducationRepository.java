package com.intern.portfolio.dao.repository;

import com.intern.portfolio.dao.entity.EducationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EducationRepository extends JpaRepository<EducationEntity, Long> {

    List<EducationEntity> findAllEducationsByUserId(Long userId);
}
