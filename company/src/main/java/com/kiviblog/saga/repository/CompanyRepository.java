package com.kiviblog.saga.repository;

import com.kiviblog.saga.domain.company.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author yangzifeng
 */
@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, String> {

}
