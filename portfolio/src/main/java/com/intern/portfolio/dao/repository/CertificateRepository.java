package com.intern.portfolio.dao.repository;

import com.intern.portfolio.dao.entity.CertificateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CertificateRepository extends JpaRepository<CertificateEntity, Long> {

    List<CertificateEntity> findAllCertificatesByUserId(Long userId);
}
