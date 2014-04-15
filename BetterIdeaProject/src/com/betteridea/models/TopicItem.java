package com.betteridea.models;

import org.json.JSONException;
import org.json.JSONObject;

public class TopicItem extends Item{
	private int id;
	private int topicID;
	private String title;
	private String description;
	private String timestamp;
	private boolean isRouletteItem;
	
	public TopicItem(String title, String description, String timestamp, boolean isRouletteItem){
		this.title = title;
		this.description = description;
		this.timestamp = timestamp;
		this.isRouletteItem = isRouletteItem;
	}
	
	public TopicItem(JSONObject jsobject){
		try{
			this.title = jsobject.getString("title");
			this.description = jsobject.getString("description");
		}catch(JSONException ex){
			
		}
	}
	
	public boolean isRouletteItem(){
		return isRouletteItem;
	}
	
	public void setRouletteItem(boolean isRouletteItem){
		this.isRouletteItem = isRouletteItem;
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
