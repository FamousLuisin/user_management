package com.sea.api.mock;

import java.util.ArrayList;
import java.util.List;

import com.sea.api.dto.request.PhoneRequestDTO;
import com.sea.api.dto.response.PhoneResponseDTO;
import com.sea.api.model.Phone;
import com.sea.api.model.Phone.PhoneType;

public class MockPhone {

    private Long id = 1L;
    private String number = "11987654321";
    private PhoneType type = PhoneType.CELULAR;

    public MockPhone withType(PhoneType type) {
        this.type = type;
        return this;
    }

    public MockPhone withNumber(String number) {
        this.number = number;
        return this;
    }

    public MockPhone withId(Long id) {
        this.id = id;
        return this;
    }

    public PhoneRequestDTO buildPhoneRequestDTO() {
        return new PhoneRequestDTO(number, type.name());
    }

    public List<PhoneRequestDTO> buildPhoneRequestDTOList() {
        List<PhoneRequestDTO> phones = new ArrayList<>();
        phones.add(new PhoneRequestDTO(number, type.name()));
        return phones;
    }

    public PhoneResponseDTO buildPhoneResponseDTO() {
        return new PhoneResponseDTO(id, number, type.name());
    }

    public List<PhoneResponseDTO> buildPhoneResponseDTOList() {
        List<PhoneResponseDTO> phones = new ArrayList<>();
        phones.add(new PhoneResponseDTO(id, number, type.name()));

        return phones;
    }

    public Phone buildPhone() {
        return new Phone(id, number, type);
    }

    public List<Phone> buildPhoneList() {
        List<Phone> phones = new ArrayList<>(); 
        phones.add(new Phone(id, number, type));
        
        return phones;
    }
}
