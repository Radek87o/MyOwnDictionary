package com.radek.myownplengdictionary.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.radek.myownplengdictionary.entity.Role;
import com.radek.myownplengdictionary.entity.User;
import com.radek.myownplengdictionary.repository.RoleRepository;
import com.radek.myownplengdictionary.repository.UserRepository;
import com.radek.myownplengdictionary.user.AppUser;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public void save(AppUser appUser) {
		User theUser = new User();
		theUser.setUsername(appUser.getUsername());
		theUser.setFirstName(appUser.getFirstName());
		theUser.setLastName(appUser.getLastName());
		theUser.setEmail(appUser.getEmail());
		theUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
		theUser.setRole(roleRepository.findByName("ROLE_USER"));

		userRepository.save(theUser);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User theUser = userRepository.findByUsername(username);
		if(theUser==null) {
			throw new UsernameNotFoundException("Invalid username or password");
		}
		return new org.springframework.security.core.userdetails.User(theUser.getUsername(), theUser.getPassword(), mapRolesToAuthorities(Arrays.asList(theUser.getRole())));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

}
