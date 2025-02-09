package com.intern.portfolio.dao.repository;

import com.intern.portfolio.dao.entity.SkillEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<SkillEntity, Long> {

    List<SkillEntity> findAllSkillsByUserId(Long userId);
}
