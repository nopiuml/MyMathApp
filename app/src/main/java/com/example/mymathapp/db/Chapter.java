package com.example.mymathapp.db;

public class Chapter {

	int chapter_id;
	String chapter_text;
	
	public Chapter(int chapter_id){
		this.chapter_id = chapter_id;
	}
	
	public Chapter(int chapter_id, String chapter_text){
		this.chapter_id = chapter_id;
		this.chapter_text = chapter_text;
	}
	
	public Chapter(){
		
	}

	public int getChapter_id() {
		return chapter_id;
	}

	public void setChapter_id(int chapter_id) {
		this.chapter_id = chapter_id;
	}

	public String getChapter_text() {
		return chapter_text;
	}

	public void setChapter_text(String chapter_text) {
		this.chapter_text = chapter_text;
	}
	
	
}
