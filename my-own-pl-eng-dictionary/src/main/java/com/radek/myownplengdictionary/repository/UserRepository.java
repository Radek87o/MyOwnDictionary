package com.radek.myownplengdictionary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.radek.myownplengdictionary.entity.User;

//repository to login and registration purposes
public interface UserRepository extends JpaRepository<User, Integer> {
	
	User findByUsername(String username);
}
