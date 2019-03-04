package com.radek.myownplengdictionary.repository;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.radek.myownplengdictionary.entity.Dictionary;
import com.radek.myownplengdictionary.entity.DictionaryStats;
import com.radek.myownplengdictionary.entity.User;

@Repository
public class DictionaryDaoJpaImpl implements DictionaryDao {
	
	@Autowired
	private EntityManager entityManager;

	@Override
	public List<Dictionary> findAll() {
		TypedQuery<Dictionary> theQuery = entityManager.createQuery("from Dictionary", Dictionary.class);
		List<Dictionary> dictionary = theQuery.getResultList();
		return dictionary;
	}

	@Override
	public List<Dictionary> findByUser(int userId) {
		TypedQuery<Dictionary> theQuery = entityManager.createQuery("select d from Dictionary d inner join d.user u where u.id=:userId", Dictionary.class);
		theQuery.setParameter("userId", userId);
		List<Dictionary> dictionary = theQuery.getResultList();
		return dictionary;
	}

	@Override
	public void createDictionary(Dictionary dictionary, int userId) {
		User theUser = entityManager.find(User.class, userId);
		dictionary.setUser(theUser);
		entityManager.merge(dictionary);
	}

	@Override
	public Dictionary findById(int dictionaryId) {
		Dictionary theDictionary = entityManager.find(Dictionary.class, dictionaryId);
		return theDictionary;
	}

	@Override
	public void deleteDictionary(int dictionaryId) {
		Dictionary theDictionary = entityManager.find(Dictionary.class, dictionaryId);
		entityManager.remove(theDictionary);
	}

	@Override
	public void createDictionaryStat(int dictionaryId, boolean result) {
		Dictionary theDictionary = entityManager.find(Dictionary.class, dictionaryId);
		DictionaryStats theDictionaryStat = new DictionaryStats(result);
		theDictionaryStat.setDictionary(theDictionary);
		entityManager.merge(theDictionaryStat);
	}
	
	//user stats
	
	@Override
	public List<?> findDictionaryTotalStatsByUser(int userId) {
		String jpql = "select d.id, d.englishWord, d.polishWord, count(ds.result)"
					+" from DictionaryStats ds inner join ds.dictionary d"
					+" inner join d.user u where u.id=:userId group by d.id order by d.id asc";
		
		Query theQuery = entityManager.createQuery(jpql);
		theQuery.setParameter("userId", userId);
		List stats = theQuery.getResultList();
		Iterator iterator = stats.iterator();
		return stats;
	}

	@Override
	public List<?> findDictionarySuccessStatsByUser(int userId) {
		String jpql = "select d.id, count(ds.result)"
				+"from DictionaryStats ds inner join ds.dictionary d"
				+" inner join d.user u where u.id=:userId and ds.result=true group by d.id order by d.id asc";
		Query theQuery = entityManager.createQuery(jpql);
		theQuery.setParameter("userId", userId);
		List stats = theQuery.getResultList();
		return stats;
	}
	
	//admin stats
	
	@Override
	public List<?> findDictionaryTotalStatsByUser() {
		String jpql = "select d.id, d.englishWord, d.polishWord, count(ds.result)"
				+" from DictionaryStats ds inner join ds.dictionary d"
				+" group by d.id order by d.id asc";
		Query theQuery = entityManager.createQuery(jpql);
		List stats = theQuery.getResultList();
		return stats;
	}

	@Override
	public List<?> findDictionarySuccessStatsByUser() {
		String jpql = "select d.id, count(ds.result)"
				+" from DictionaryStats ds inner join ds.dictionary d"
				+" where ds.result=true group by d.id order by d.id asc";
		Query theQuery = entityManager.createQuery(jpql);
		List stats = theQuery.getResultList();
		return stats;
	}

	@Override
	public List<?> findDictionaryStatsByAdmin() {
		String jpql = "select u.username, count(distinct d.englishWord), max(ds.entryDate) from DictionaryStats ds"
					+" right join ds.dictionary d inner join d.user u where u.id<>1 group by u.id order by count(d.englishWord) desc";
		Query theQuery = entityManager.createQuery(jpql);
		List adminList = theQuery.getResultList();
		return adminList;
	}

	@Override
	public long findDictionaryUsersNumber() {
		String jpql = "select count(username) from User u inner join u.role r where r.id<>1";
		TypedQuery<Long> numberOfUsers = entityManager.createQuery(jpql, Long.class);
		Long usersNumber = numberOfUsers.getSingleResult();
		return usersNumber;
	}

	@Override
	public List<Dictionary> findSearchDictionary(String theSearchName) {
		Query theQuery = null;
		if(theSearchName!=null && theSearchName.trim().length()>0) {
			String jpql = "from Dictionary where lower(englishWord) like :searchName or lower(polishWord) like :searchName";
			theQuery=entityManager.createQuery(jpql, Dictionary.class);
			theQuery.setParameter("searchName", "%"+theSearchName.toLowerCase()+"%");
		}
		else {
			theQuery=entityManager.createQuery("from Dictionary", Dictionary.class);
		}
		
		List<Dictionary> searchList = theQuery.getResultList();
		return searchList;
	}

	@Override
	public List<Dictionary> findSearchDictionary(String theSearchName, int userId) {
		Query theQuery = null;
		if(theSearchName!=null && theSearchName.trim().length()>0) {
			String jpql = "select d from Dictionary d inner join d.user u where u.id=:userId and lower(d.englishWord) like :searchName or lower(d.polishWord) like :searchName";
			theQuery=entityManager.createQuery(jpql, Dictionary.class);
			theQuery.setParameter("userId", userId);
			theQuery.setParameter("searchName", "%"+theSearchName.toLowerCase()+"%");
		}
		else {
			theQuery=entityManager.createQuery("select d from Dictionary d inner join d.user u where u.id=:userId", Dictionary.class);
			theQuery.setParameter("userId", userId);
		}
		
		List<Dictionary> searchList = theQuery.getResultList();
		return searchList;
	}

	@Override
	public long findUserWordsNumber(int userId) {
		TypedQuery<Long> theQuery = entityManager.createQuery("select count(d.englishWord) from Dictionary d inner join d.user u where u.id=:userId", Long.class);
		theQuery.setParameter("userId", userId);
		Long userWordsNumber = theQuery.getSingleResult();
		return userWordsNumber;
	}

	@Override
	public long findAllWordsNumber() {
		TypedQuery<Long> theQuery = entityManager.createQuery("select count(d.englishWord) from Dictionary d", Long.class);
		Long allWordsNumber = theQuery.getSingleResult();
		return allWordsNumber;
	}

	
}
