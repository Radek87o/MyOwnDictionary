package com.radek.myownplengdictionary.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.radek.myownplengdictionary.entity.Dictionary;
import com.radek.myownplengdictionary.entity.User;
import com.radek.myownplengdictionary.service.DictionaryService;
import com.radek.myownplengdictionary.service.UserService;
import com.radek.myownplengdictionary.user.AppUserStat;

@Controller
@RequestMapping("/dictionary")
public class DictionaryController {
	
	//all the methods defining usage of application
	
	@Autowired
	private DictionaryService dictionaryService;

	@Autowired
	private UserService userService;

	@InitBinder
	public void dataBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmer = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmer);
	}
	
	//CRUD for Dictionary.class
	
	//create dictionary record
	@GetMapping("/addWord")
	public String addWord(Model theModel) {
		// retrieving user details based on user authentication through spring security filters
		org.springframework.security.core.userdetails.User theUser = (org.springframework.security.core.userdetails.User) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String username = theUser.getUsername();
		//retrieving User.class Object by username
		User myUser = userService.findByUsername(username);
		
		//depending on authority - admin can display list of all dictionary records contributed by all the app users -'fullDictionary'
		//users can display only their own list of words - 'userDictionary'
		List<Dictionary> fullDictionary = dictionaryService.findAll();
		List<Dictionary> userDictionary = dictionaryService.findByUser(myUser.getId());
		theModel.addAttribute("dictionary", new Dictionary());
		theModel.addAttribute("fullDictionary", fullDictionary);
		theModel.addAttribute("userDictionary", userDictionary);
		return "dictionary-form";
	}
	
	//save dictionary record into db
	@PostMapping("/saveWord")
	public String saveWord(@Valid @ModelAttribute(name="dictionary") Dictionary dictionary, BindingResult theBindingResult, Model theModel) {
		//validation of dictionary form
		if(theBindingResult.hasErrors()) {
			return "dictionary-form";
		}
		
		if(dictionary.getExpression()!=null) {
			if(dictionary.getExpression().length()>255) {
				theModel.addAttribute("expressionInvalid", "Expression must be shorter than 256 chars");
				return "dictionary-form";
			}
		}
		
		
		//retrieving userId to set the user adding the dictionary record  and finally merge the record into db
		org.springframework.security.core.userdetails.User theUser = (org.springframework.security.core.userdetails.User) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String username = theUser.getUsername();
		User myUser = userService.findByUsername(username);
		int userId=myUser.getId();
		
		dictionaryService.createDictionary(dictionary, userId);
		
		return "redirect:/dictionary/addWord";
	}
	
	//update dictionary record
	@GetMapping("/updateWord")
	public String updateWord(@RequestParam(name="dictionaryId") int dictionaryId, Model theModel) {
		Dictionary theDictionary = dictionaryService.findById(dictionaryId);
		theModel.addAttribute("dictionary", theDictionary);
		return "dictionary-form";
	}
	
	//delete dictionary record
	@GetMapping("/deleteWord")
	public String deleteWord(@RequestParam(name="dictionaryId") int dictionaryId) {
		dictionaryService.deleteDictionary(dictionaryId);
		return "redirect:/dictionary/addWord";
	}
	
	//Test yourself section - the idea behind the section is opportunity to effectively repeat vocabulary 
	//by drawing random word from user's custom vocabulary list - see CRUD for Dictionary.class above
	//Admin can draw words from the full list of vocabulary contributed by users, but his results are not returned to user stats
	
	//1. Draw english word from vocabulary list
	@GetMapping("/drawEngPol")
	public String drawWordEngPl(Model theModel) {
		org.springframework.security.core.userdetails.User theUser = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = theUser.getUsername();
		User myUser = userService.findByUsername(username);
		int userId = myUser.getId();
		
		if(myUser.getRole().getName().equals("ROLE_USER")) {
			Dictionary theDictionary = dictionaryService.findRandomDictionary(userId);
			if(theDictionary==null) {
				return "test-warning-before";
			}
			theModel.addAttribute("dictionary", theDictionary);
		}
		
		if(myUser.getRole().getName().equals("ROLE_ADMIN")) {
			Dictionary adminDictionary = dictionaryService.findRandomDictionary();
			if(adminDictionary==null) {
				return "test-warning-before";
			}
			theModel.addAttribute("adminDictionary", adminDictionary);
		}
		
		return "test-eng-pl";
	}
	
	//2. If user click "I guess it" button - word is specified as guessed and the result is returned to Dictionary Stats table
	@GetMapping("/statsSuccessEngPl")
	public String successGuessEng(@RequestParam(name="dictionaryId") int dictionaryId, Model theModel) {
		dictionaryService.createDictionaryStat(dictionaryId, true);
		return "redirect:/dictionary/drawEngPol";
	}
	
	//3. If user click "I got it wrong" button - word is specified as wrong and the result is returned to Dictionary Stats table
	@GetMapping("/statsWrongEngPl")
	public String wrongGuessEng(@RequestParam(name="dictionaryId") int dictionaryId, Model theModel) {
		dictionaryService.createDictionaryStat(dictionaryId, false);
		return "redirect:/dictionary/drawEngPol";
	}
	
	//Analogical procedure for polish words
	
	@GetMapping("/drawPolEng")
	public String drawWordPlEng(Model theModel) {
		org.springframework.security.core.userdetails.User theUser = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = theUser.getUsername();
		User myUser = userService.findByUsername(username);
		int userId = myUser.getId();
		if(myUser.getRole().getName().equals("ROLE_USER")) {
			Dictionary theDictionary = dictionaryService.findRandomDictionary(userId);
			if(theDictionary==null) {
				return "test-warning-before";
			}
			theModel.addAttribute("dictionary", theDictionary);
		}
		
		if(myUser.getRole().getName().equals("ROLE_ADMIN")) {
			Dictionary adminDictionary = dictionaryService.findRandomDictionary();
			if(adminDictionary==null) {
				return "test-warning-before";
			}
			theModel.addAttribute("adminDictionary", adminDictionary);
		}
		
		return "test-pl-eng";
	}
	
	@GetMapping("/statsSuccessPlEng")
	public String successGuessPl(@RequestParam(name="dictionaryId") int dictionaryId, Model theModel) {
		dictionaryService.createDictionaryStat(dictionaryId, true);
		return "redirect:/dictionary/drawPolEng";
	}
	
	@GetMapping("/statsWrongPlEng")
	public String wrongGuessPL(@RequestParam(name="dictionaryId") int dictionaryId, Model theModel) {
		dictionaryService.createDictionaryStat(dictionaryId, false);
		return "redirect:/dictionary/drawPolEng";
	}
	
	//List of vocabulary for a specific user including number of success attempts, total attempts and efficiency for each word
	//vocabulary list is ordered ascending by efficiency
	//The idea behind this section is to give user ability to easily find the most difficult words to remember
	@GetMapping("/userStats")
	public String userStats(Model theModel) {
		org.springframework.security.core.userdetails.User theUser = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = theUser.getUsername();
		User myUser = userService.findByUsername(username);
		int userId = myUser.getId();
		List<AppUserStat> appUserStats = dictionaryService.findAppUserStatsByUser(userId);
		theModel.addAttribute("appUserStats", appUserStats);
		List<AppUserStat> appAdminStats = dictionaryService.findAppUserStatsByUser();
		theModel.addAttribute("appAdminStats", appAdminStats);
		long userWordsNumber = dictionaryService.findUserWordsNumber(userId);
		theModel.addAttribute("userWordsNumber", userWordsNumber);
		long allWordsNumber = dictionaryService.findAllWordsNumber();
		theModel.addAttribute("allWordsNumber", allWordsNumber);
		return "user-stats";
	}
	
	@GetMapping("/wordDetails")
	public String wordDetails(@RequestParam(name="dictionaryId") int dictionaryId, Model theModel) {
		Dictionary theDictionary = dictionaryService.findById(dictionaryId);
		theModel.addAttribute("dictionary", theDictionary);
		return "word-details";
	}
	
	//search word from own custom dictionary
	//admin can search from full list of vocabulary
	//search is active both for Polish and English Words
	
	@GetMapping("/searchWord")
	public String searchWord(@RequestParam(name="theSearchName") String searchName, Model theModel) {
		org.springframework.security.core.userdetails.User theUser = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = theUser.getUsername();
		User myUser = userService.findByUsername(username);
		int userId = myUser.getId();
		List<Dictionary> fullSearchList = dictionaryService.findSearchDictionary(searchName);
		List<Dictionary> userDictionary = dictionaryService.findSearchDictionary(searchName, userId);
		theModel.addAttribute("dictionary", new Dictionary());
		theModel.addAttribute("fullDictionary", fullSearchList);
		theModel.addAttribute("userDictionary", userDictionary);
		return "dictionary-form";
	}
}
