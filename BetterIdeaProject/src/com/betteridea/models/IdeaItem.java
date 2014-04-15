package com.betteridea.models;

public class IdeaItem extends Item{
	private String text;
//	private int user;
	private String user;
	private String date;
	
	public IdeaItem(String text, String user, String date){
		this.text = text;
		this.user = user;
		this.date = date;
	}
	
	public String getText(){
		return text;
	}
	
	public String getUser(){
		return user;
	}
	
	public String getDate(){
		return date;
	}
}
