package com.sea.api.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.sea.api.dto.request.ClientRequestDTO;
import com.sea.api.dto.request.ClientUpdateDTO;
import com.sea.api.dto.request.EmailRequestDTO;
import com.sea.api.dto.request.PhoneRequestDTO;
import com.sea.api.dto.response.AddressResponseDTO;
import com.sea.api.dto.response.ClientResponseDTO;
import com.sea.api.dto.response.EmailResponseDTO;
import com.sea.api.dto.response.PhoneResponseDTO;
import com.sea.api.model.Address;
import com.sea.api.model.Client;
import com.sea.api.model.Email;
import com.sea.api.model.Phone;
import com.sea.api.model.Phone.PhoneType;

public class MockClient {
    
    private Long id = 1L;
    private String name = "John Doe";
    private String cpf = "719.687.700-46";
    private String cep = "01001000";

    public MockClient withName(String name) {
        this.name = name;
        return this;
    }

    public MockClient withCpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    public MockClient withId(Long id){
        this.id = id;
        return this;
    }

    public ClientRequestDTO buildClientRequestDTO(){
        List<PhoneRequestDTO> phoneRequests = new ArrayList<>();
        List<EmailRequestDTO> emailRequests = new ArrayList<>();


        return new ClientRequestDTO(name, cpf, cep, phoneRequests, emailRequests);
    }

    public ClientRequestDTO buildClientRequestDTO(List<PhoneRequestDTO> phonesRequest, List<EmailRequestDTO> emailRequest){
        return new ClientRequestDTO(name, cpf, cep, phonesRequest, emailRequest);
    }

    public ClientUpdateDTO buildClientUpdateDTO(){
        return new ClientUpdateDTO(name, cpf);
    }

    public ClientResponseDTO buildClientResponseDTO(List<PhoneResponseDTO> phones, List<EmailResponseDTO> emails, AddressResponseDTO address){
        return new ClientResponseDTO(id, name, cpf, address, phones, emails);
    }

    public ClientResponseDTO buildClientResponseDTO(){
        List<PhoneResponseDTO> phoneResponse = new ArrayList<>();
        List<EmailResponseDTO> emailResponse = new ArrayList<>();

        return new ClientResponseDTO(null, name, cpf, null, phoneResponse, emailResponse);
    }

    public Client buildClient(List<Phone> phones, List<Email> emails, Address address){
        return new Client(null, name, cpf, address, phones.stream().collect(Collectors.toSet()), emails.stream().collect(Collectors.toSet()));
    }

    public Client buildClient(){
        Set<Phone> phones = new ArrayList<Phone>().stream().collect(Collectors.toSet());
        Set<Email> emails = new ArrayList<Email>().stream().collect(Collectors.toSet());

        return new Client(1L, name, cpf, null, phones, emails);
    }

    public List<Client> buildClientList(Integer n){
        List<Client> clients = new ArrayList<>();
        String emailFormat = "email%s@email.com";
        String phoneFormat = "11987654321";

        for (int i = 0; i < n; i++) {
            Client client = buildClient();
            Phone phone = new Phone(Integer.toUnsignedLong(i), phoneFormat, PhoneType.CELULAR);
            Email email = new Email(Integer.toUnsignedLong(i), String.format(emailFormat, i));
            Address address = new Address(Integer.toUnsignedLong(i), "01001000", "Praça da Sé", "Sé", "São Paulo", "SP", "");

            client.getPhones().add(phone);
            client.getEmails().add(email);
            client.setAddress(address);
            clients.add(client);
        }
        return clients;
    }

    public List<ClientResponseDTO> buildClientResponseDTOList(Integer n){
        List<ClientResponseDTO> clients = new ArrayList<>();
        String emailFormat = "email%s@email.com";
        String phoneFormat = "11987654321";

        for (int i = 0; i < n; i++) {
            ClientResponseDTO client = buildClientResponseDTO();
            PhoneResponseDTO phone = new PhoneResponseDTO(Integer.toUnsignedLong(i), phoneFormat, "CELULAR");
            EmailResponseDTO email = new EmailResponseDTO(Integer.toUnsignedLong(i), String.format(emailFormat, i));
            AddressResponseDTO address = new AddressResponseDTO(Integer.toUnsignedLong(i), "01001000", "Praça da Sé", "Sé", "São Paulo", "SP", "");

            client.getPhones().add(phone);
            client.getEmails().add(email);
            client.setAddress(address);
            clients.add(client);
        }
        return clients;

    }
}
