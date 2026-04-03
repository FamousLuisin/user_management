package com.sea.api.validation.cep;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.sea.api.utils.NormalizeFields;

public class CepValidator implements ConstraintValidator<Cep, String> {

    @Override
    public boolean isValid(String cep, ConstraintValidatorContext context) {
        cep = NormalizeFields.normalize(cep);

        return validateCEP(cep);
    }

    private boolean validateCEP(String cep) {
        if (cep == null || !cep.matches("[0-9]{8}")) {
            return false;
        }

        return true;
    }
    
}
