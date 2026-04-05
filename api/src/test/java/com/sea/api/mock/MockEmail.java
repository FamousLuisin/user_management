package com.sea.api.mock;

import java.util.ArrayList;
import java.util.List;

import com.sea.api.dto.request.EmailRequestDTO;
import com.sea.api.dto.response.EmailResponseDTO;
import com.sea.api.model.Email;

public class MockEmail {
    
    private Long id = 1L;
    private String email = "email@email.com";

    public MockEmail withEmail(String email){
        this.email = email;
        return this;
    }

    public MockEmail withId(Long id){
        this.id = id;
        return this;
    }

    public EmailRequestDTO buildEmailRequestDTO(){
        return new EmailRequestDTO(email);
    }

    public List<EmailRequestDTO> buildEmailRequestDTOList(){
        List<EmailRequestDTO> emails = new ArrayList<>();
        emails.add(new EmailRequestDTO(email));
        return emails;
    }

    public EmailResponseDTO buildEmailResponseDTO(){
        return new EmailResponseDTO(id, email);
    }

    public List<EmailResponseDTO> buildEmailResponseDTOList(){
        List<EmailResponseDTO> emails = new ArrayList<>();
        emails.add(new EmailResponseDTO(id, email));
        return emails;
    }

    public Email buildEmail(){
        return new Email(id, email);
    }

    public List<Email> buildEmailList(){
        List<Email> emails = new ArrayList<>();
        emails.add(new Email(id, email));

        return emails;
    }
}
