package com.radek.myownplengdictionary.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.radek.myownplengdictionary.validation.EmailValid;
import com.radek.myownplengdictionary.validation.FieldMatch;


//helper class defined to process User.class objects validation
@FieldMatch.List(@FieldMatch(first="password", second="matchingPassword", message="The passwords specified must be identical"))
public class AppUser {
	
	@NotNull(message = "This field cannot be empty")
	@Size(min = 1, message = "This field cannot be empty")
	private String username;
	
	@NotNull(message="This field cannot be empty")
	@Size(min = 1, message = "This field cannot be empty")
	private String password;
	
	@NotNull(message="This field cannot be empty")
	@Size(min = 1, message = "This field cannot be empty")
	private String matchingPassword;
	
	@NotNull(message = "This field cannot be empty")
	@Size(min = 1, message = "This field cannot be empty")
	private String firstName;

	@NotNull(message = "This field cannot be empty")
	@Size(min = 1, message = "This field cannot be empty")
	private String lastName;
	
	@EmailValid
	private String email;
	
	public AppUser() {
		// TODO Auto-generated constructor stub
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMatchingPassword() {
		return matchingPassword;
	}

	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "AppUser [username=" + username + ", password=" + password + ", matchingPassword=" + matchingPassword
				+ ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + "]";
	}
	
	
}
