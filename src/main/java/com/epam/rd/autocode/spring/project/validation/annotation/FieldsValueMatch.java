package com.epam.rd.autocode.spring.project.validation.annotation;

import com.epam.rd.autocode.spring.project.validation.validator.FieldsValueMatchValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target({ TYPE })
@Constraint(validatedBy = {FieldsValueMatchValidator.class })
public @interface FieldsValueMatch {

    String message() default "Fields doesn't match";
    String field();
    String fieldMatch();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    @Target({ TYPE })
    @Retention(RUNTIME)
    @interface List {
        FieldsValueMatch[] value();
    }
}
