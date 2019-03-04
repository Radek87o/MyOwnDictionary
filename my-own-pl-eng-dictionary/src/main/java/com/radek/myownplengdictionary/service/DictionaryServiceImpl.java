package com.radek.myownplengdictionary.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.header.writers.frameoptions.StaticAllowFromStrategy;
import org.springframework.stereotype.Service;

import com.radek.myownplengdictionary.entity.Dictionary;
import com.radek.myownplengdictionary.repository.DictionaryDao;
import com.radek.myownplengdictionary.user.AppUserRecord;
import com.radek.myownplengdictionary.user.AppUserStat;

@Service
public class DictionaryServiceImpl implements DictionaryService {

	@Autowired
	private DictionaryDao dictionaryDao;

	@Override
	@Transactional
	public List<Dictionary> findAll() {
		return dictionaryDao.findAll();
	}

	@Override
	@Transactional
	public List<Dictionary> findByUser(int userId) {
		return dictionaryDao.findByUser(userId);
	}

	@Override
	@Transactional
	public Dictionary findById(int dictionaryId) {
		return dictionaryDao.findById(dictionaryId);
	}

	@Override
	@Transactional
	public void createDictionary(Dictionary dictionary, int userId) {
		dictionaryDao.createDictionary(dictionary, userId);
	}

	@Override
	@Transactional
	public void deleteDictionary(int dictionaryId) {
		dictionaryDao.deleteDictionary(dictionaryId);
	}
	
	//drawing word form full list of vocabulary contributed to the app by all users
	
	@Override
	@Transactional
	public Dictionary findRandomDictionary() {
		List<Dictionary> adminDictionary = dictionaryDao.findAll();
		int randomPosition = (int) (Math.random() * adminDictionary.size());
		if(adminDictionary.size()>0) {
			Dictionary randomDictionary = adminDictionary.get(randomPosition);
			return randomDictionary;
		}
		else {
			return null;
		}
	}
	
	//drawing word form custom user vocabulary list

	@Override
	@Transactional
	public Dictionary findRandomDictionary(int userId) {
		List<Dictionary> userDictionary = dictionaryDao.findByUser(userId);
		int randomPosition = (int) (Math.random() * userDictionary.size());
		if(userDictionary.size()>0) {
			Dictionary randomDictionary = userDictionary.get(randomPosition);
			return randomDictionary;
		}
		else {
			return null;
		}
	}

	@Override
	@Transactional
	public void createDictionaryStat(int dictionaryId, boolean result) {
		dictionaryDao.createDictionaryStat(dictionaryId, result);
	}
	
	//User stats
	
	@Override
	@Transactional
	public List<AppUserStat> findAppUserStatsByUser(int userId) {
		List<AppUserStat> appUserStatsList = new ArrayList<AppUserStat>();
		List<?> totalUserStats = dictionaryDao.findDictionaryTotalStatsByUser(userId);
		Iterator<?> iterator = totalUserStats.iterator();
		while(iterator.hasNext()) {
			Object[] item = (Object[]) iterator.next();
			int dictionaryId = (int) item[0];
			String englishWord = (String) item[1];
			String polishWord = (String) item[2];
			long totalAttempts = (long) item[3];
			AppUserStat tempUserStat = new AppUserStat(dictionaryId, englishWord, polishWord, totalAttempts);
			appUserStatsList.add(tempUserStat);
		}
		
		List<?> successUserStats = dictionaryDao.findDictionarySuccessStatsByUser(userId);
		Iterator<?> iterator2 = successUserStats.iterator();
		while(iterator2.hasNext()) {
			Object[] item = (Object[]) iterator2.next();
			int dictionaryId = (int) item[0];
			long successAttempts = (long) item[1];
			for(AppUserStat tempUserStat: appUserStatsList) {
				if(tempUserStat.getDictionaryId()==dictionaryId) {
					tempUserStat.setSuccessAttempts(successAttempts);
				}
			}
		}
		
		//calculate efficiency for each stat record
		for(AppUserStat tempUserStat: appUserStatsList) {
			double efficiency = (double)tempUserStat.getSuccessAttempts()/(double)tempUserStat.getTotalAttempts();
			tempUserStat.setEfficiency(efficiency);
		}
		
		//sort List of user stats according by efficiency ascending
		Collections.sort(appUserStatsList, (a,b)->a.getEfficiency()<b.getEfficiency()?-1:a.getEfficiency()<b.getEfficiency()?0:1);
		
		return appUserStatsList;
	}
	
	//Admin stats
	
	@Override
	@Transactional
	public List<AppUserStat> findAppUserStatsByUser() {
		List<AppUserStat> appUserStatsList = new ArrayList<AppUserStat>();
		List<?> totalUserStats = dictionaryDao.findDictionaryTotalStatsByUser();
		Iterator<?> iterator = totalUserStats.iterator();
		while(iterator.hasNext()) {
			Object[] item = (Object[]) iterator.next();
			int dictionaryId = (int) item[0];
			String englishWord = (String) item[1];
			String polishWord = (String) item[2];
			long totalAttempts = (long) item[3];
			AppUserStat tempUserStat = new AppUserStat(dictionaryId, englishWord, polishWord, totalAttempts);
			appUserStatsList.add(tempUserStat);
		}
		
		List<?> successUserStats = dictionaryDao.findDictionarySuccessStatsByUser();
		Iterator<?> iterator2 = successUserStats.iterator();
		while(iterator2.hasNext()) {
			Object[] item = (Object[]) iterator2.next();
			int dictionaryId = (int) item[0];
			long successAttempts = (long) item[1];
			for(AppUserStat tempUserStat: appUserStatsList) {
				if(tempUserStat.getDictionaryId()==dictionaryId) {
					tempUserStat.setSuccessAttempts(successAttempts);
				}
			}
		}
		
		//calculate efficiency for each stat record
		for(AppUserStat tempUserStat: appUserStatsList) {
			double efficiency = (double)tempUserStat.getSuccessAttempts()/(double)tempUserStat.getTotalAttempts();
			tempUserStat.setEfficiency(efficiency);
		}
		
		//sort List of user stats according by efficiency ascending
		Collections.sort(appUserStatsList, (a,b)->a.getEfficiency()<b.getEfficiency()?-1:a.getEfficiency()<b.getEfficiency()?0:1);
		
		return appUserStatsList;
	}

	@Override
	@Transactional
	public List<AppUserRecord> findAppUserOverallStats() {
		List<AppUserRecord> appUserRecords = new ArrayList<AppUserRecord>();
		List<?> overallStats = dictionaryDao.findDictionaryStatsByAdmin();
		if(overallStats!=null) {
			Iterator<?> iterator = overallStats.iterator();
			while(iterator.hasNext()) {
				Object[] item = (Object[]) iterator.next();
				String username = (String) item[0];
				long wordsNumber = (long) item[1];
				Date lastActivity = (Date) item[2];
				AppUserRecord theAppUserRecord = new AppUserRecord(username, wordsNumber, lastActivity);
				appUserRecords.add(theAppUserRecord);
			}
		}
		return appUserRecords;
	}

	@Override
	@Transactional
	public long findDictionaryUsersNumber() {
		return dictionaryDao.findDictionaryUsersNumber();
	}

	@Override
	@Transactional
	public List<Dictionary> findSearchDictionary(String theSearchName) {
		return dictionaryDao.findSearchDictionary(theSearchName);
	}

	@Override
	@Transactional
	public List<Dictionary> findSearchDictionary(String theSearchName, int userId) {
		return dictionaryDao.findSearchDictionary(theSearchName, userId);
	}

	@Override
	@Transactional
	public long findUserWordsNumber(int userId) {
		return dictionaryDao.findUserWordsNumber(userId);
	}

	@Override
	@Transactional
	public long findAllWordsNumber() {
		return dictionaryDao.findAllWordsNumber();
	}
	
	
	
	
}
