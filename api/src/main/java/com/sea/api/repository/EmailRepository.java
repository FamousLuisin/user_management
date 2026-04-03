package com.sea.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sea.api.model.Email;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {
    
    boolean existsByClientIdAndIdNot(Long clientId, Long phoneId);
}
