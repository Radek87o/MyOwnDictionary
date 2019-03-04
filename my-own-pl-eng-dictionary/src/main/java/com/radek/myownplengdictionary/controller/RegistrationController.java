package com.radek.myownplengdictionary.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.radek.myownplengdictionary.entity.User;
import com.radek.myownplengdictionary.service.UserService;
import com.radek.myownplengdictionary.user.AppUser;

@Controller
@RequestMapping("/register")
public class RegistrationController {
	
	@Autowired
	private UserService userService;
	
	//in order to disable assigning a sequence of whitespaces as field value and transform it into null value
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmer = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmer);
	}
	
	@GetMapping("/registrationForm")
	public String registerUser(Model theModel) {
		theModel.addAttribute("appUser", new AppUser());
		return "registration-form";
	}
	
	@PostMapping("processRegistrationForm")
	public String processRegistrationForm(@Valid @ModelAttribute(name="appUser") AppUser theAppUser, BindingResult theBindingResult, Model theModel) {
		
		//validation based on annotations defined for User.class
		if(theBindingResult.hasErrors()) {
			return "registration-form";
		}
		String username = theAppUser.getUsername();
		
		//if appUser was successfully validated - need to check if the specified username already exists, because it has to be unique
		//if so, registration form and registration error message should be returned
		User existingUser = userService.findByUsername(username);
		if(existingUser!=null) {
			theModel.addAttribute("appUser", new AppUser());
			theModel.addAttribute("registrationError", "The username specified is already in use");
			return "registration-form";
		}
		
		//create user
		userService.save(theAppUser);
		return "registration-confirmation";
	}
}
