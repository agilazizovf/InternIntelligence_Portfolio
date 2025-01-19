package com.intern.portfolio.dao.repository;

import com.intern.portfolio.dao.entity.SocialMediaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SocialMediaRepository extends JpaRepository<SocialMediaEntity, Long> {

    List<SocialMediaEntity> findAllSocialMediasByUserId(Long userId);
}
