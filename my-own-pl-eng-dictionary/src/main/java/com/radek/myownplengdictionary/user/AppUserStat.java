package com.radek.myownplengdictionary.user;

public class AppUserStat {
	
	
	//helper class to retrieve user stats by joining Dictionary.class, DictionaryStats.class, User.class
	
	private int dictionaryId;
	private String englishWord;
	private String polishWord;
	private long successAttempts;
	private long totalAttempts;
	private double efficiency;
	
	
	public AppUserStat(int dictionaryId, String englishWord, String polishWord, long totalAttempts) {
		this.dictionaryId = dictionaryId;
		this.englishWord = englishWord;
		this.polishWord = polishWord;
		this.totalAttempts = totalAttempts;
	}
	public int getDictionaryId() {
		return dictionaryId;
	}
	public void setDictionaryId(int dictionaryId) {
		this.dictionaryId = dictionaryId;
	}
	public String getEnglishWord() {
		return englishWord;
	}
	public void setEnglishWord(String englishWord) {
		this.englishWord = englishWord;
	}
	public String getPolishWord() {
		return polishWord;
	}
	public void setPolishWord(String polishWord) {
		this.polishWord = polishWord;
	}
	public long getSuccessAttempts() {
		return successAttempts;
	}
	public void setSuccessAttempts(long successAttempts) {
		this.successAttempts = successAttempts;
	}
	public long getTotalAttempts() {
		return totalAttempts;
	}
	public void setTotalAttempts(long totalAttempts) {
		this.totalAttempts = totalAttempts;
	}
	public double getEfficiency() {
		return efficiency;
	}
	public void setEfficiency(double efficiency) {
		this.efficiency = efficiency;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(efficiency);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AppUserStat other = (AppUserStat) obj;
		if (Double.doubleToLongBits(efficiency) != Double.doubleToLongBits(other.efficiency))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "AppUserStat [dictionaryId=" + dictionaryId + ", englishWord=" + englishWord + ", polishWord="
				+ polishWord + ", successAttempts=" + successAttempts + ", totalAttempts=" + totalAttempts
				+ ", efficiency=" + efficiency + "]";
	}
	
	
}
