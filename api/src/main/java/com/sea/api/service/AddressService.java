package com.sea.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sea.api.dto.request.AddressRequestDTO;
import com.sea.api.dto.request.CepRequestDTO;
import com.sea.api.dto.response.AddressResponseDTO;
import com.sea.api.exception.NotFoundAddressException;
import com.sea.api.mapper.Mapper;
import com.sea.api.model.Address;
import com.sea.api.repository.AddressRepository;

@Service
public class AddressService {
    
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private Mapper mapper;

    @Autowired
    private ViaCepClientService viaCepClientService;

    public AddressResponseDTO updateAddress(Long id, AddressRequestDTO request){
        Address address = addressRepository.findById(id).orElseThrow(() -> new NotFoundAddressException(id));

        address.setBairro(request.getBairro());
        address.setCidade(request.getCidade());
        address.setComplemento(request.getComplemento());
        address.setLogradouro(request.getLogradouro());
        address.setUf(request.getUf());

        addressRepository.save(address);

        return mapper.parseObject(address, AddressResponseDTO.class);
    }

    public AddressResponseDTO updateCep(Long id, CepRequestDTO request){
        Address address = addressRepository.findById(id).orElseThrow(() -> new NotFoundAddressException(id));
        AddressRequestDTO addressRequestDTO = viaCepClientService.viaCepClient(request.getCep());

        address.setBairro(addressRequestDTO.getBairro());
        address.setCidade(addressRequestDTO.getCidade());
        address.setComplemento(addressRequestDTO.getComplemento());
        address.setLogradouro(addressRequestDTO.getLogradouro());
        address.setUf(addressRequestDTO.getUf());

        addressRepository.save(address);

        return mapper.parseObject(address, AddressResponseDTO.class);
    }

    public AddressResponseDTO findAddressById(Long id) {
        Address address = addressRepository.findById(id).orElseThrow(() -> new NotFoundAddressException(id));

        return mapper.parseObject(address, AddressResponseDTO.class);
    }

    public AddressResponseDTO findAddressByClientId(Long clientId) {
        Address address = addressRepository.findByClientId(clientId);

        return mapper.parseObject(address, AddressResponseDTO.class);
    }
}
