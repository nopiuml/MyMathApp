package com.example.mymathapp.db;

public class UserAnswers {
	
	int user_id;
	int question_id;
	String user_answer;
	int error;
	
	public UserAnswers(){
		
	}
	public UserAnswers(int user_id, int question_id, String user_answer, int error){
		this.user_id = user_id;
		this.question_id = question_id;
		this.user_answer = user_answer;
		this.error = error;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getQuestion_id() {
		return question_id;
	}

	public void setQuestion_id(int question_id) {
		this.question_id = question_id;
	}

	public String getUser_answer() {
		return user_answer;
	}

	public void setUser_answer(String user_answer) {
		this.user_answer = user_answer;
	}

	public int getError() {
		return error;
	}

	public void setError(int error) {
		this.error = error;
	}
	

}
