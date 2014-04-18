package com.betteridea.models;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class TopicItem extends Item{
	private int id;
	private int topicID;
	private boolean archived;
	private String updated;
	private String title;
	private String description;
	private String timestamp;
	private boolean isRouletteItem;
	private int authorID;
	
	public TopicItem(String title, String description, String timestamp, boolean isRouletteItem){
		this.title = title;
		this.description = description;
		this.timestamp = timestamp;
		this.isRouletteItem = isRouletteItem;
	}
	
	public TopicItem(JSONObject jsobject){
		Log.v("test", "JSONObject:  " + jsobject.toString());
		try{
//			JSONObject auslesen
			this.setID(jsobject.getInt("id"));
			this.setArchived(jsobject.getBoolean("archived"));
			this.setUpdated(jsobject.getString("updated"));
			this.title = jsobject.getString("titel");
			this.description = jsobject.getString("description");
			this.setTopicID(jsobject.getInt("topicID"));
			this.timestamp = jsobject.getString("date");
			this.setAuthorID(jsobject.getInt("authorID"));
			
//			Wichtige Information für Adapter: Ist das Element ein RouletteItem?
			this.isRouletteItem = true;
		}catch(JSONException ex){
			Log.v("test", "JSONException: " + ex.toString());
		}
	}
	
	//Eigene Topics (kommen nicht ins Roulette) 
	//TODO: Damit aber noch nicht aus dem Roulette gänzlich ausgeschlossen!
	public TopicItem(JSONObject jsobject, boolean ownTopic){
		Log.v("test", "JSONObject:  " + jsobject.toString());
		try{
//			JSONObject auslesen
			this.setID(jsobject.getInt("id"));
			this.title = jsobject.getString("titel");
			this.setUpdated(jsobject.getString("updated"));
			this.description = jsobject.getString("description");
			this.setTopicID(jsobject.getInt("topicID"));
			this.timestamp = jsobject.getString("date");
			
			this.isRouletteItem = false;
		}catch(JSONException ex){
			Log.v("test", "JSONException: " + ex.toString());
		}
	}
	
	
	/*
	 * GETTER & SETTER
	 */
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

	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}

	public int getTopicID() {
		return topicID;
	}

	public void setTopicID(int topicID) {
		this.topicID = topicID;
	}

	public boolean isArchived() {
		return archived;
	}

	public void setArchived(boolean archived) {
		this.archived = archived;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public int getAuthorID() {
		return authorID;
	}

	public void setAuthorID(int authorID) {
		this.authorID = authorID;
	}
}
