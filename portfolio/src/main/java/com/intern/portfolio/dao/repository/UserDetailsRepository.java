package com.intern.portfolio.dao.repository;

import com.intern.portfolio.dao.entity.UserDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetailsEntity, Long> {

    boolean existsByUserId(Long userId);

}
