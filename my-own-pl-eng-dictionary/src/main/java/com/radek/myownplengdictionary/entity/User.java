package com.radek.myownplengdictionary.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@NotNull(message = "This field cannot be empty")
	@Size(min = 1, message = "This field cannot be empty")
	@Column(name = "username")
	private String username;
	
	@Column(name="password")
	private String password;
	
	@NotNull(message = "This field cannot be empty")
	@Size(min = 1, message = "This field cannot be empty")
	@Column(name = "first_name")
	private String firstName;

	@NotNull(message = "This field cannot be empty")
	@Size(min = 1, message = "This field cannot be empty")
	@Column(name = "last_name")
	private String lastName;

	@Column(name = "email")
	private String email;
	
	@ManyToOne(cascade= {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="role_id")
	private Role role;
	
	@OneToMany(mappedBy="user")
	private List<Dictionary> dictionary;
	
	public User() {
		// TODO Auto-generated constructor stub
	}

	

	public User(
			@NotNull(message = "This field cannot be empty") @Size(min = 1, message = "This field cannot be empty") String username,
			String password,
			@NotNull(message = "This field cannot be empty") @Size(min = 1, message = "This field cannot be empty") String firstName,
			@NotNull(message = "This field cannot be empty") @Size(min = 1, message = "This field cannot be empty") String lastName,
			String email) {
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
	
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	
	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}



	public List<Dictionary> getDictionary() {
		return dictionary;
	}

	public void setDictionary(List<Dictionary> dictionary) {
		this.dictionary = dictionary;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + "]";
	}

}
