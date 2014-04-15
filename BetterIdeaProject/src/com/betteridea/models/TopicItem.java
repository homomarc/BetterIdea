package com.betteridea.models;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

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
		Log.v("test", "TopicItem");
		try{
			this.title = jsobject.getString("titel");
			Log.v("test", "Title: " + title);
			this.description = jsobject.getString("description");
			Log.v("test", "Description: " + description);
			this.isRouletteItem = true;
			this.timestamp = "Test";
		}catch(JSONException ex){
			Log.v("test", "JSONException: " + ex.toString());
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
