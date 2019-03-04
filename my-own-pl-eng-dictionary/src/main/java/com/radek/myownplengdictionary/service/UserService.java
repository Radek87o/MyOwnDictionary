package com.radek.myownplengdictionary.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.radek.myownplengdictionary.entity.User;
import com.radek.myownplengdictionary.user.AppUser;

public interface UserService extends UserDetailsService{
	User findByUsername(String username);
	void save(AppUser appUser);
}
