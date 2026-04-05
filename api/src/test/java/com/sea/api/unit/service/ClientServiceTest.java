package com.sea.api.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.sea.api.dto.request.AddressRequestDTO;
import com.sea.api.dto.request.ClientRequestDTO;
import com.sea.api.dto.request.ClientUpdateDTO;
import com.sea.api.dto.request.EmailRequestDTO;
import com.sea.api.dto.request.PhoneRequestDTO;
import com.sea.api.dto.response.AddressResponseDTO;
import com.sea.api.dto.response.ClientResponseDTO;
import com.sea.api.dto.response.EmailResponseDTO;
import com.sea.api.dto.response.PhoneResponseDTO;
import com.sea.api.mapper.Mapper;
import com.sea.api.mock.MockAddress;
import com.sea.api.mock.MockClient;
import com.sea.api.mock.MockEmail;
import com.sea.api.mock.MockPhone;
import com.sea.api.model.Address;
import com.sea.api.model.Client;
import com.sea.api.model.Email;
import com.sea.api.model.Phone;
import com.sea.api.repository.ClientRepository;
import com.sea.api.service.ClientService;
import com.sea.api.service.ViaCepClientService;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private Mapper mapper;

    @Mock
    private ViaCepClientService viaCepClientService;

    private MockClient mockClient;
    private MockAddress mockAddress;
    private MockPhone mockPhone;
    private MockEmail mockEmail;

    @BeforeEach
    void setUp(){
        this.mockClient = new MockClient();
        this.mockAddress = new MockAddress();
        this.mockPhone = new MockPhone();
        this.mockEmail = new MockEmail();
    }

    @Test
    void testCreateClient() {
        AddressRequestDTO addressRequestMock = mockAddress.builAddressRequestDTO();
        Address addressMock = mockAddress.buildAddress();
        AddressResponseDTO addressResponseMock = mockAddress.buildAddressResponseDTO();

        List<Phone> phoneMock = mockPhone.buildPhoneList();
        List<PhoneRequestDTO> phoneRequestMock = mockPhone.buildPhoneRequestDTOList();
        List<PhoneResponseDTO> phoneResponseMock = mockPhone.buildPhoneResponseDTOList();

        List<Email> emailMock = mockEmail.buildEmailList();
        List<EmailRequestDTO> emailRequestMock = mockEmail.buildEmailRequestDTOList();
        List<EmailResponseDTO> emailResponseMock = mockEmail.buildEmailResponseDTOList();
        
        ClientRequestDTO clientRequetsMock = mockClient.buildClientRequestDTO(phoneRequestMock, emailRequestMock);
        Client clientMock = mockClient.buildClient(phoneMock, emailMock, addressMock);
        ClientResponseDTO clientResponseMock = mockClient.withCpf("71968770046").buildClientResponseDTO(phoneResponseMock, emailResponseMock, addressResponseMock);

        when(viaCepClientService.viaCepClient(clientRequetsMock.getCep())).thenReturn(addressRequestMock);
        when(mapper.parseObject(addressRequestMock, Address.class)).thenReturn(addressMock);
        when(mapper.parseListObject(clientRequetsMock.getPhones(), Phone.class)).thenReturn(phoneMock);
        when(mapper.parseListObject(clientRequetsMock.getEmails(), Email.class)).thenReturn(emailMock);
        when(mapper.parseObject(clientRequetsMock, Client.class)).thenReturn(clientMock);
        when(clientRepository.save(clientMock)).then(a -> {
            clientMock.setId(1L);
            return clientMock;
        });
        when(mapper.parseObject(clientMock, ClientResponseDTO.class)).thenReturn(clientResponseMock);

        clientService.createClient(clientRequetsMock);

        assertNotNull(clientResponseMock);
        assertNotNull(clientResponseMock.getId());
        assertNotNull(clientResponseMock.getEmails());
        assertNotNull(clientResponseMock.getPhones());
        assertNotNull(clientResponseMock.getAddress());

        assertEquals(1L, clientResponseMock.getId());
        assertEquals("71968770046", clientResponseMock.getCpf());
        assertEquals("John Doe", clientResponseMock.getName());

        verifyNoMoreInteractions(mapper);
        verify(clientRepository, times(1)).save(any(Client.class));
    }

    @Test
    void testDeleteClientById() {
        Client clientMock = mockClient.buildClient();

        when(clientRepository.findById(1L)).thenReturn(Optional.of(clientMock));

        clientService.deleteClientById(1L);

        verify(clientRepository, times(1)).findById(anyLong());
        verify(clientRepository, times(1)).delete(any(Client.class));
        verifyNoMoreInteractions(clientRepository);
    }

    @Test
    void testFindAllClients() {
        Pageable pageable = PageRequest.of(0, 2);
        List<Client> clientMock = mockClient.buildClientList(2);
        List<ClientResponseDTO> clientResponseMock = mockClient.buildClientResponseDTOList(2);
        Page<Client> mockPage = new PageImpl<>(clientMock);
        AtomicInteger index = new AtomicInteger();

        when(clientRepository.findAll(pageable)).thenReturn(mockPage);
        when(mapper.parseObject(any(Client.class), eq(ClientResponseDTO.class)))
            .thenAnswer(a -> clientResponseMock.get(index.getAndIncrement()));

        clientService.findAllClients(pageable);

        assertNotNull(clientResponseMock);
        assertEquals(2, clientResponseMock.size());
        assertEquals("John Doe", clientResponseMock.get(0).getName());
        assertEquals(0L, clientResponseMock.get(0).getPhones().get(0).getId());
        assertEquals("email0@email.com", clientResponseMock.get(0).getEmails().get(0).getEmail());
    }

    @Test
    void testFindClientById() {
        Client clientMock = mockClient.buildClient();

        List<PhoneResponseDTO> phoneMock = mockPhone.buildPhoneResponseDTOList();
        List<EmailResponseDTO> emailMock = mockEmail.buildEmailResponseDTOList();
        AddressResponseDTO addressMock = mockAddress.buildAddressResponseDTO();

        ClientResponseDTO clientResponseMock = mockClient.withCpf("71968770046").buildClientResponseDTO(phoneMock, emailMock, addressMock);

        when(clientRepository.findById(1L)).thenReturn(Optional.of(clientMock));
        when(mapper.parseObject(clientMock, ClientResponseDTO.class)).thenReturn(clientResponseMock);

        clientService.findClientById(1L);

        assertNotNull(clientResponseMock);
        assertEquals("John Doe", clientResponseMock.getName());
        assertEquals("71968770046", clientResponseMock.getCpf());
        assertEquals(1L, clientResponseMock.getPhones().get(0).getId());
        assertEquals("email@email.com", clientResponseMock.getEmails().get(0).getEmail());
        assertEquals("Praça da Sé", clientResponseMock.getAddress().getLogradouro());
    }

    @Test
    void testUpdateClientById() {
        Client clientMock = mockClient.buildClient();
        ClientUpdateDTO clientUpdateMock = mockClient.buildClientUpdateDTO();
        ClientResponseDTO clientResponseMock = mockClient.withCpf("71968770046").buildClientResponseDTO();

        when(clientRepository.findById(1L)).thenReturn(Optional.of(clientMock));
        when(mapper.parseObject(clientMock, ClientResponseDTO.class)).thenReturn(clientResponseMock);

        clientService.updateClientById(1L, clientUpdateMock);

        assertNotNull(clientResponseMock);
        assertEquals("John Doe", clientResponseMock.getName());
        assertEquals("71968770046", clientResponseMock.getCpf());
    }
}
