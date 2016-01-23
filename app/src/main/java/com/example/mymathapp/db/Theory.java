package com.example.mymathapp.db;

public class Theory {

	int theory_id;
	int chapter_id;
	String theory_text;
	String pictures;
	
	public Theory(int theory_id, String theory_text, String pictures){
		this.theory_id = theory_id;
		this.theory_text = theory_text;
		this.pictures = pictures;
		
	}
	
	public Theory(int chapter_id, int theory_id, String theory_text, String pictures){
		this.chapter_id = chapter_id;
		this.theory_id = theory_id;
		this.theory_text = theory_text;
		this.pictures = pictures;
		
	}

	public Theory() {
		// TODO Auto-generated constructor stub
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

	public String getTheory_text() {
		return theory_text;
	}

	public void setTheory_text(String theory_text) {
		this.theory_text = theory_text;
	}

	public String getPictures() {
		return pictures;
	}

	public void setPictures(String pictures) {
		this.pictures = pictures;
	}
	
}
