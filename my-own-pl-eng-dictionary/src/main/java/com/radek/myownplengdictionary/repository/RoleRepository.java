package com.radek.myownplengdictionary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.radek.myownplengdictionary.entity.Role;

//repository to login and registration purposes
public interface RoleRepository extends JpaRepository<Role, Integer>{
	
	Role findByName(String name);
}
