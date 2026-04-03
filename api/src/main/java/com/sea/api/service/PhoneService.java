package com.sea.api.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.sea.api.dto.request.PhoneRequestDTO;
import com.sea.api.dto.response.PhoneResponseDTO;
import com.sea.api.exception.NotFoundClientException;
import com.sea.api.exception.NotFoundPhoneException;
import com.sea.api.mapper.Mapper;
import com.sea.api.model.Client;
import com.sea.api.model.Phone;
import com.sea.api.model.Phone.PhoneType;
import com.sea.api.repository.ClientRepository;
import com.sea.api.repository.PhoneRepository;

@Service
public class PhoneService {
    
    @Autowired
    private PhoneRepository phoneRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private Mapper mapper;

    public PhoneResponseDTO updatePhone(Long id, PhoneRequestDTO request) {
        Phone phone = phoneRepository.findById(id).orElseThrow(() -> new NotFoundPhoneException(id));

        phone.setNumber(request.getNumber());
        phone.setType(PhoneType.valueOf(request.getType().toUpperCase()));

        phoneRepository.save(phone);

        return mapper.parseObject(phone, PhoneResponseDTO.class);
    }

    @Transactional
    public void deletePhone(Long id) {
        Phone phone = phoneRepository.findById(id).orElseThrow(() -> new NotFoundPhoneException(id));

        if (!phoneRepository.existsByClientIdAndIdNot(phone.getClient().getId(), id)){ 
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "não é possivel deleter o unico telefone salvo");
        }

        phone.getClient().getPhones().remove(phone);
        phoneRepository.delete(phone);
    }

    public PhoneResponseDTO addPhone(Long userId, PhoneRequestDTO request) {
        Client client = clientRepository.findById(userId).orElseThrow(() -> new NotFoundClientException(userId));

        Phone phone = mapper.parseObject(request, Phone.class);
        phone.setClient(client);

        phoneRepository.save(phone);

        return mapper.parseObject(phone, PhoneResponseDTO.class);
    }
}
