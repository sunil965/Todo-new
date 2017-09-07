package com.bridgeit.todo.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bridgeit.todo.model.User;

public class UserRegValidate implements Validator{
	
	 private Pattern pattern;  
	 private Matcher matcher; 
	 
	 private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";  
	 private static final String STRING_PATTERN = "[a-zA-Z]+"; //-- "^([a-zA-Z' ]+)$" OR  ^[a-zA-Z\\s ]*$
	 private static final String MOBILE_PATTERN = "[0-9]{10}";
	 private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";


	public boolean supports(Class<?> arg0) {
		return false;
	}

	public void validate(Object target, Errors error) {

		User user = (User) target;
		
		ValidationUtils.rejectIfEmpty(error, "name", "required.name", "Name Is Missing.");
		// input string contains characters only  
	
		if(!(user.getName()!=null && user.getName().isEmpty())){
			pattern = Pattern.compile(STRING_PATTERN);
			matcher = pattern.matcher(user.getName());
			if(!matcher.matches()){
				error.rejectValue("name", "name.containNonChar", "Enter a valid name");
			}
		}
		
		ValidationUtils.rejectIfEmpty(error, "email", "required.email", "Email Is Missing.");
		// email validation in spring  
		if(!(user.getEmail() !=null && user.getEmail().isEmpty())){
			pattern = Pattern.compile(EMAIL_PATTERN);
			matcher = pattern.matcher(user.getEmail());
			if(!matcher.matches()){
				error.rejectValue("email", "email.containNonChar", "Enter a valid Email");
			}
		}
		
		
		ValidationUtils.rejectIfEmpty(error, "contact", "required.contact", "Contact Is Missing.");
		// phone number validation  
		if(!(user.getContact() !=null && user.getContact().isEmpty())){
			pattern = Pattern.compile(MOBILE_PATTERN);
			matcher = pattern.matcher(user.getContact());
			if(!matcher.matches()){
				error.rejectValue("contact", "contact.incorrect", "Enter a valid contact");
			}
		}
		
		ValidationUtils.rejectIfEmpty(error, "password", "required.password", "Password Is Missing.");
		// password number validation  
		if(!(user.getPassword() !=null && user.getPassword().isEmpty())){
			pattern = Pattern.compile(PASSWORD_PATTERN);
			matcher = pattern.matcher(user.getPassword());
			if(!matcher.matches()){
				error.rejectValue("password", "password.incorrect", "Enter a valid password");
			}
		}
	}

}
