package com.radek.myownplengdictionary.service;

import java.util.List;

import com.radek.myownplengdictionary.entity.Dictionary;
import com.radek.myownplengdictionary.user.AppUserRecord;
import com.radek.myownplengdictionary.user.AppUserStat;

public interface DictionaryService {
	
	List<Dictionary> findAll();
	List<Dictionary> findByUser(int userId);
	Dictionary findById(int dictionaryId);
	void createDictionary(Dictionary dictionary, int userId);
	void deleteDictionary(int dictionaryId);
	Dictionary findRandomDictionary();
	Dictionary findRandomDictionary(int userId);
	void createDictionaryStat(int dictionaryId, boolean result);
	List<AppUserStat> findAppUserStatsByUser(int userId);
	List<AppUserStat> findAppUserStatsByUser();
	List<AppUserRecord> findAppUserOverallStats();
	long findDictionaryUsersNumber();
	List<Dictionary> findSearchDictionary(String theSearchName);
	List<Dictionary> findSearchDictionary(String theSearchName, int userId);
	long findUserWordsNumber(int userId);
	long findAllWordsNumber();
}
