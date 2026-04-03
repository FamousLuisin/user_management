package com.sea.api.validation.phone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.sea.api.dto.request.PhoneRequestDTO;
import com.sea.api.utils.NormalizeFields;

public class PhoneValidator implements ConstraintValidator<Phone, PhoneRequestDTO>{

    @Override
    public boolean isValid(PhoneRequestDTO phone, ConstraintValidatorContext context) {
        if (phone == null) return false;

        String phoneNumber = NormalizeFields.normalize(phone.getNumber());
        String phoneType = phone.getType();

        if (!validatePhone(phoneNumber) || !validateType(phoneType)) return false;

        switch (phoneType.toUpperCase()) {
            case "CELULAR":
                return phoneNumber.matches("[0-9]{11}");
            case "RESIDENCIAL":
                return phoneNumber.matches("[0-9]{10}");
            case "COMERCIAL":
                return phoneNumber.matches("[0-9]{10}");
            default:
                return false;
        }
    }

    private boolean validatePhone(String phone) {
        if (phone == null || phone.isEmpty()) {
            return false;
        }

        return true;
    }

    private boolean validateType(String type) {
        if (type == null || type.isEmpty()) {
            return false;
        }

        return true;
    }
    
}
