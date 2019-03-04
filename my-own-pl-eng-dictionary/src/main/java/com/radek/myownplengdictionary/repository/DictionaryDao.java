package com.radek.myownplengdictionary.repository;

import java.util.List;

import com.radek.myownplengdictionary.entity.Dictionary;

public interface DictionaryDao {
	List<Dictionary> findAll();
	List<Dictionary> findByUser(int userId);
	Dictionary findById(int dictionaryId);
	void createDictionary(Dictionary dictionary, int userId);
	void deleteDictionary(int dictionaryId);
	void createDictionaryStat(int dictionaryId, boolean result);
	List<?> findDictionaryTotalStatsByUser(int userId);
	List<?> findDictionarySuccessStatsByUser(int userId);
	List<?> findDictionaryTotalStatsByUser();
	List<?> findDictionarySuccessStatsByUser();
	List<?> findDictionaryStatsByAdmin();
	long findDictionaryUsersNumber();
	List<Dictionary> findSearchDictionary(String theSearchName);
	List<Dictionary> findSearchDictionary(String theSearchName, int userId);
	long findUserWordsNumber(int userId);
	long findAllWordsNumber();
}
