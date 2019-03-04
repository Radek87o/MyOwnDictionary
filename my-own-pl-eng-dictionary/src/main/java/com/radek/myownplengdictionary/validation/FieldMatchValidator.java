package com.radek.myownplengdictionary.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

	private String firstFieldName;
	private String secondFieldName;
	private String message;

	@Override
	public void initialize(FieldMatch constraintAnnotation) {
		firstFieldName = constraintAnnotation.first();
		secondFieldName = constraintAnnotation.second();
		message = constraintAnnotation.message();
	}

	@Override
	public boolean isValid(final Object value, ConstraintValidatorContext context) {
		boolean valid = true;
		try {
			final Object firstObject = new BeanWrapperImpl(value).getPropertyValue(firstFieldName);
			final Object secondObject = new BeanWrapperImpl(value).getPropertyValue(secondFieldName);

			valid = firstObject == null && secondObject == null
					|| firstObject != null && secondObject!=null && secondObject.equals(firstObject);

		} catch (BeansException exc) {
			exc.printStackTrace();
		}

		if (!valid) {
			context.buildConstraintViolationWithTemplate(message).addPropertyNode(firstFieldName)
					.addConstraintViolation().disableDefaultConstraintViolation();
		}
		return valid;
	}

}
