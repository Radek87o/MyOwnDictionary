package com.radek.myownplengdictionary.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="dictionary")
public class Dictionary {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@NotNull(message="This field cannot be empty")
	@Size(min=1, max=40 ,message="This field cannot be empty and word length cannot exceed 40 chars")
	@Column(name="english_word")
	private String englishWord;
	
	@NotNull(message="This field cannot be empty")
	@Size(min=1, max=40 ,message="This field cannot be empty and word length cannot exceed 40 chars")
	@Column(name="polish_word")
	private String polishWord;
	
	private String expression;
	
	@ManyToOne(cascade= {CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinColumn(name="user_id")
	private User user;
	
	@OneToMany(mappedBy="dictionary", cascade=CascadeType.ALL)
	private List<DictionaryStats> dictionaryStats;
	
	public Dictionary() {
		// TODO Auto-generated constructor stub
	}
	
	public Dictionary(
			@NotNull(message = "This field cannot be empty") @Size(min = 1, message = "This field cannot be empty") String englishWord,
			@NotNull(message = "This field cannot be empty") @Size(min = 1, message = "This field cannot be empty") String polishWord,
			String expression) {
		this.englishWord = englishWord;
		this.polishWord = polishWord;
		this.expression = expression;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Dictionary [id=" + id + ", englishWord=" + englishWord + ", polishWord=" + polishWord + ", expression="
				+ expression + ", user=" + user + "]";
	}
	
}
