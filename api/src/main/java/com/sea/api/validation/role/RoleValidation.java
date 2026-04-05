package com.sea.api.validation.role;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ java.lang.annotation.ElementType.FIELD })
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RoleTypeValidator.class)
public @interface RoleValidation {
    
    String message() default "Invalid role";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
