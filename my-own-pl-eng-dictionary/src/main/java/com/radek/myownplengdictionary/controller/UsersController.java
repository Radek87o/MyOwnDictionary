package com.radek.myownplengdictionary.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.radek.myownplengdictionary.service.DictionaryService;
import com.radek.myownplengdictionary.user.AppUserRecord;

@Controller
@RequestMapping("/users")
public class UsersController {

	//defining the methods available only for admins
	@Autowired
	private DictionaryService dictionaryService;
	
	//returns full list of users, total number of users and a brief piece of information about their activity, that means:
	//number of words contributed to the app, datetime of last activity defined as attempt to guess drawn word
	@GetMapping("/list")
	public String usersList(Model theModel) {
		List<AppUserRecord> appUserOverallStats = dictionaryService.findAppUserOverallStats();
		long usersNumber = dictionaryService.findDictionaryUsersNumber();
		theModel.addAttribute("overallStats", appUserOverallStats);
		theModel.addAttribute("usersNumber", usersNumber);
		return "user-list";
	}
}
