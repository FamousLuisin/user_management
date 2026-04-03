package com.sea.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sea.api.dto.request.AddressRequestDTO;
import com.sea.api.dto.request.ClientRequestDTO;
import com.sea.api.dto.request.ClientUpdateDTO;
import com.sea.api.dto.response.ClientResponseDTO;
import com.sea.api.exception.NotFoundClientException;
import com.sea.api.mapper.Mapper;
import com.sea.api.model.Address;
import com.sea.api.model.Client;
import com.sea.api.model.Email;
import com.sea.api.model.Phone;
import com.sea.api.repository.ClientRepository;
import com.sea.api.utils.NormalizeFields;

@Service
public class ClientService {
    
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ViaCepClientService viaCepClientService;

    @Autowired
    private Mapper mapper;

    public ClientResponseDTO createClient(ClientRequestDTO request){
        AddressRequestDTO addressDTO = viaCepClientService.viaCepClient(NormalizeFields.normalize(request.getCep()));
        Address address = mapper.parseObject(addressDTO, Address.class);
        address.setCep(NormalizeFields.normalize(request.getCep()));

        List<Phone> phones = mapper.parseListObject(request.getPhones(), Phone.class);
        List<Email> emails = mapper.parseListObject(request.getEmails(), Email.class);

        Client client = mapper.parseObject(request, Client.class);
        client.setCpf(NormalizeFields.normalize(request.getCpf()));
        client.setAddress(address);
        client.setPhones(phones.stream().collect(Collectors.toSet()));
        client.setEmails(emails.stream().collect(Collectors.toSet()));
        
        address.setClient(client);
        phones.forEach(p -> p.setClient(client));
        emails.forEach(e -> e.setClient(client));

        clientRepository.save(client);
             
        return mapper.parseObject(client, ClientResponseDTO.class);
    }

    public List<ClientResponseDTO> findAllClients(Pageable pageable){
        Page<Client> clients = clientRepository.findAll(pageable);

        Page<ClientResponseDTO> clientsDTO = clients.map(c -> mapper.parseObject(c, ClientResponseDTO.class));
        
        return clientsDTO.toList();
    }

    public ClientResponseDTO findClientById(Long id){
        Client client = clientRepository.findById(id).orElseThrow(() -> new NotFoundClientException(id));

        ClientResponseDTO response = mapper.parseObject(client, ClientResponseDTO.class);

        return response;
    }

    public ClientResponseDTO updateClientById(Long id, ClientUpdateDTO request){
        Client client = clientRepository.findById(id).orElseThrow(() -> new NotFoundClientException(id));

        client.setName(request.getName());
        client.setCpf(NormalizeFields.normalize(request.getCpf()));
        clientRepository.save(client);

        return mapper.parseObject(client, ClientResponseDTO.class);
    }

    public void deleteClientById(Long id){
        Client client = clientRepository.findById(id).orElseThrow(() -> new NotFoundClientException(id));

        clientRepository.delete(client);
    }
}
