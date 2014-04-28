package com.betteridea.models;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class IdeaItem extends Item{
	private String text = "";
//	private int user;
	private String date = "";
	private boolean isValuated = false;
	private String id = "";
	private boolean uncovered = false;
	private int ideaID = -1;
	private int topicID = -1;
	private String authorID = "-1";
	
	public IdeaItem(String text, String date, boolean isValuated, String id, boolean uncovered, int ideaID, int topicID, String authorID){
		this.text = text;
		this.date = date;
		this.setValuated(isValuated);
		this.setId(id);
		this.setUncovered(uncovered);
		this.setIdeaID(ideaID);
		this.setTopicID(topicID);
		this.setAuthorID(authorID);
	}
	
	public IdeaItem(JSONObject obj){
		try{
			this.text = obj.getString("text");
			this.date = obj.getString("date");
			this.isValuated = obj.getBoolean("isValuated");
			this.id = obj.getString("id");
			this.uncovered = obj.getBoolean("uncovered");
			this.ideaID = obj.getInt("ideaID");
			this.topicID = obj.getInt("topicID");
			this.authorID = obj.getString("authorID");
		}catch(JSONException ex){
			Log.v("text", "JSONException: " + ex.toString());
		}
	}
	
	public String getText(){
		return text;
	}
	
	public String getDate(){
		return date;
	}

	public boolean isValuated() {
		return isValuated;
	}

	public void setValuated(boolean isValuated) {
		this.isValuated = isValuated;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isUncovered() {
		return uncovered;
	}

	public void setUncovered(boolean uncovered) {
		this.uncovered = uncovered;
	}

	public int getIdeaID() {
		return ideaID;
	}

	public void setIdeaID(int ideaID) {
		this.ideaID = ideaID;
	}

	public int getTopicID() {
		return topicID;
	}

	public void setTopicID(int topicID) {
		this.topicID = topicID;
	}

	public String getAuthorID() {
		return authorID;
	}

	public void setAuthorID(String authorID) {
		this.authorID = authorID;
	}
}