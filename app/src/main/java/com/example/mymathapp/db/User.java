package com.example.mymathapp.db;

public class User {
	
	int user_id;
	String user_name;
	int new_user;
	
	public User(int user_id, String user_name, int new_user){
		this.user_id = user_id;
		this.user_name = user_name;
		this.new_user = new_user;
	}
	
	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public int getNew_user() {
		return new_user;
	}

	public void setNew_user(int new_user) {
		this.new_user = new_user;
	}

	public User(){
		
	}
	
}
