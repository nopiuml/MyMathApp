package com.example.mymathapp.db;

public class UserStats {

	int user_id;
	String chapter;
	int question_id;
	int correct_answer_id;
	int wrong_answer_id;
	int blank_answer_id;
	double success;
	
	public UserStats() {
		// TODO Auto-generated constructor stub
	}
	
	public UserStats(int user_id, String chapter, int question_id, int correct_answer_id, int wrong_answer_id, int blank_answer_id, double success) {
		
		this.user_id = user_id;
		this.chapter = chapter;
		this.question_id = question_id;
		this.correct_answer_id = correct_answer_id;
		this.wrong_answer_id = wrong_answer_id;
		this.blank_answer_id = blank_answer_id;
		this.success = success;
		// TODO Auto-generated constructor stub
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	

	public String getChapter() {
		return chapter;
	}

	public void setChapter(String chapter) {
		this.chapter = chapter;
	}

	public int getQuestion_id() {
		return question_id;
	}

	public void setQuestion_id(int question_id) {
		this.question_id = question_id;
	}

	public int getCorrect_answer_id() {
		return correct_answer_id;
	}

	public void setCorrect_answer_id(int correct_answer_id) {
		this.correct_answer_id = correct_answer_id;
	}

	public int getWrong_answer_id() {
		return wrong_answer_id;
	}

	public void setWrong_answer_id(int wrong_answer_id) {
		this.wrong_answer_id = wrong_answer_id;
	}

	public int getBlank_answer_id() {
		return blank_answer_id;
	}

	public void setBlank_answer_id(int blank_answer_id) {
		this.blank_answer_id = blank_answer_id;
	}

	public double getSuccess() {
		return success;
	}

	public void setSuccess(double success) {
		this.success = success;
	}

	
}
