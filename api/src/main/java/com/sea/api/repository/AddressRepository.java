package com.sea.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sea.api.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    
    Address findByClientId(Long id);
}
