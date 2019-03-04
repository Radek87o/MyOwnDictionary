package com.radek.myownplengdictionary.user;

import java.util.Date;

public class AppUserRecord {
	
	//helper class defined to retrieve overall stats regarding number of words per user, and his last activity
	//data retrieved by joining Dictionary.class, DictionaryStats.class and User.class
	//this data is returned to UsersController (section available only for admins)
	
	private String username;
	private long wordsNumber;
	private Date lastActivity;
	
	public AppUserRecord() {
		// TODO Auto-generated constructor stub
	}
	
	
	public AppUserRecord(String username, long wordsNumber, Date lastActivity) {
		this.username = username;
		this.wordsNumber = wordsNumber;
		this.lastActivity = lastActivity;
	}


	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public long getWordsNumber() {
		return wordsNumber;
	}
	public void setWordsNumber(long wordsNumber) {
		this.wordsNumber = wordsNumber;
	}
	public Date getLastActivity() {
		return lastActivity;
	}
	public void setLastActivity(Date lastActivity) {
		this.lastActivity = lastActivity;
	}


	@Override
	public String toString() {
		return "AppUserRecord [username=" + username + ", wordsNumber=" + wordsNumber + ", lastActivity=" + lastActivity
				+ "]";
	}
}
