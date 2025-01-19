package com.intern.portfolio.dao.repository;

import com.intern.portfolio.dao.entity.LanguageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LanguageRepository extends JpaRepository<LanguageEntity, Long> {

    List<LanguageEntity> findAllLanguagesByUserId(Long userId);
}
