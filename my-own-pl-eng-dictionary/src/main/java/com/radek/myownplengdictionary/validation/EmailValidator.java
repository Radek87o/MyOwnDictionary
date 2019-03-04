package com.radek.myownplengdictionary.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<EmailValid, String> {
	
	private static Pattern pattern;
	private Matcher matcher;
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	@Override
	public boolean isValid(final String email, ConstraintValidatorContext context) {
		pattern=Pattern.compile(EMAIL_PATTERN);
		if(email==null) {
			return true;
		}
		matcher=pattern.matcher(email);
		return matcher.matches();
	}
	
}
