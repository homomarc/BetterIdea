package com.betteridea.models;

public class TopicItem {
	private String title;
	private String description;
	private String timestamp;
	
	public TopicItem(String title, String description, String timestamp){
		this.title = title;
		this.description = description;
		this.timestamp = timestamp;
	}
	
	public String getTitle(){
		return title;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public String getDescription(){
		return description;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public String getTimestamp(){
		return timestamp;
	}
	
	public void setTimestamp(String timestamp){
		this.timestamp = timestamp;
	}
}
