package com.sea.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sea.api.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    
    boolean existsByCpf(String cpf);

    Client findByCpf(String cpf);

    Page<Client> findByAddressUf(String uf, Pageable pageable);
}
