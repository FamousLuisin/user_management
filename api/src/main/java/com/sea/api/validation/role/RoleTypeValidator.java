package com.sea.api.validation.role;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RoleTypeValidator implements ConstraintValidator<RoleValidation, List<String>> {

    private static final List<String> VALID_ROLES = Arrays.asList("COMMON", "ADMIN");

    @Override
    public boolean isValid(List<String> roles, ConstraintValidatorContext context) {
        if (roles == null) {
            return false;
        }

        for (String role : roles) {
            if (!VALID_ROLES.contains(role)) {
                return false;
            }
        }
        return true;
    }
    
}
