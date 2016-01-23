package com.example.mymathapp.db;

public class Solution {

	int solution_id;
	int question_id;
	int theory_id;
	String solution_text;

	public Solution(int question_id, String solution_text){
		this.question_id = question_id;
		this.solution_text = solution_text;
		
	}
	
	public Solution(int solution_id, int theory_id, int question_id, String solution_text){
		this.solution_id = solution_id;
		this.theory_id = theory_id;
		this.question_id = question_id;
		this.solution_text = solution_text;
	}
	
	public int getTheory_id() {
		return theory_id;
	}

	public void setTheory_id(int theory_id) {
		this.theory_id = theory_id;
	}
	
	public int getSolution_id() {
		return solution_id;
	}

	public void setSolution_id(int solution_id) {
		this.solution_id = solution_id;
	}

	public int getQuestion_id() {
		return question_id;
	}

	public void setQuestion_id(int question_id) {
		this.question_id = question_id;
	}

	public String getSolution_text() {
		return solution_text;
	}

	public void setSolution_text(String solution_text) {
		this.solution_text = solution_text;
	}

	public Solution() {
		// TODO Auto-generated constructor stub
	}

}
