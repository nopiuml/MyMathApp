package com.example.mymathapp.db;

public class Exercise {

	int question_id;
	int theory_id;
	int chapter_id;
	String question_text;
	
	public Exercise(int question_id,String question_text){
		this.question_id = question_id;
		this.question_text = question_text;
		
	}
	
	public Exercise(int question_id, int theory_id, int chapter_id, String question_text){

		this.question_id = question_id;
		this.chapter_id = chapter_id;
		this.theory_id = theory_id;
		this.question_text = question_text;
	}
	
	public Exercise(){
		
	}

	public int getQuestion_id() {
		return question_id;
	}

	public void setQuestion_id(int question_id) {
		this.question_id = question_id;
	}

	public int getTheory_id() {
		return theory_id;
	}

	public void setTheory_id(int theory_id) {
		this.theory_id = theory_id;
	}

	public int getChapter_id() {
		return chapter_id;
	}

	public void setChapter_id(int chapter_id) {
		this.chapter_id = chapter_id;
	}

	public String getQuestion_text() {
		return question_text;
	}

	public void setQuestion_text(String question_text) {
		this.question_text = question_text;
	}
}
