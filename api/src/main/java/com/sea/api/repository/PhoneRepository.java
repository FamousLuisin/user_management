package com.sea.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sea.api.model.Phone;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {
    
    boolean existsByClientIdAndIdNot(Long clientId, Long phoneId);
}
