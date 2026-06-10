package com.example.guvi.order.service.customAnnotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BookExistsValidator.class)
@Documented

public @interface BookExists {

    String message() default "Book Does Not Exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
