package com.radek.myownplengdictionary.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy=EmailValidator.class)
@Target({ElementType.ANNOTATION_TYPE,ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EmailValid {
	String message() default "Invalid Email";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
