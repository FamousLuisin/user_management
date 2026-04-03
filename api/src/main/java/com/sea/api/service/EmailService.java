package com.sea.api.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.sea.api.dto.request.EmailRequestDTO;
import com.sea.api.dto.response.EmailResponseDTO;
import com.sea.api.exception.NotFoundClientException;
import com.sea.api.exception.NotFoundEmailException;
import com.sea.api.mapper.Mapper;
import com.sea.api.model.Client;
import com.sea.api.model.Email;
import com.sea.api.repository.ClientRepository;
import com.sea.api.repository.EmailRepository;

@Service
public class EmailService {
    
    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private Mapper mapper;

    public EmailResponseDTO updateEmail(Long id, EmailRequestDTO request) {
        Email email = emailRepository.findById(id).orElseThrow(() -> new NotFoundEmailException(id));

        email.setEmail(request.getEmail());

        emailRepository.save(email);

        return mapper.parseObject(email, EmailResponseDTO.class);
    }

    @Transactional
    public void deleteEmail(Long id) {
        Email email = emailRepository.findById(id).orElseThrow(() -> new NotFoundEmailException(id));

        if (!emailRepository.existsByClientIdAndIdNot(email.getClient().getId(), id)){ 
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "não é possivel deleter o unico email salvo");
        }

        email.getClient().getEmails().remove(email);
        emailRepository.delete(email);
    }

    public EmailResponseDTO addEmail(Long userId, EmailRequestDTO request) {
        Client client = clientRepository.findById(userId).orElseThrow(() -> new NotFoundClientException(userId));

        Email email = mapper.parseObject(request, Email.class);
        email.setClient(client);

        emailRepository.save(email);

        return mapper.parseObject(email, EmailResponseDTO.class);
    }
}
