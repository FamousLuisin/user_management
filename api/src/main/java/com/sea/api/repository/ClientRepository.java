package com.sea.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sea.api.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    
    boolean existsByCpf(String cpf);
}
