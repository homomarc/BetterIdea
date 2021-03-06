package com.betteridea.models;

/**
 * Author: 		Better Idea
 * Description:	Modelklasse f�r die Datenhaltung der einzelnen TopicItems, 
 * 				notwendig zur Anzeige f�r den TopicItemAdapter.
 * 
 * TODOS:		keine
 * 
 */

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class TopicItem extends Item{
	//Variablendeklaration
	private int topicID, authorID;
	private boolean archived, isRouletteItem;
	private String id, updated, title, description, timestamp;
	
	//TopicItem Konstruktor
	public TopicItem(String title, String description, String timestamp, boolean isRouletteItem){
		this.title = title;
		this.description = description;
		this.timestamp = timestamp;
		this.isRouletteItem = isRouletteItem;
	}
	
	//TopicItem Konstruktor mit JSONObject und RouletteItem = true/false
	public TopicItem(JSONObject jsobject, boolean isRouletteItem){
		try{
			// JSONObject auslesen
			this.setID(jsobject.getString("id"));
			this.setArchived(jsobject.getBoolean("archived"));
			this.setUpdated(jsobject.getString("updated"));
			this.title = jsobject.getString("titel");
			this.description = jsobject.getString("description");
			this.setTopicID(jsobject.getInt("topicID"));
			this.timestamp = jsobject.getString("date");
			this.setAuthorID(jsobject.getInt("authorID"));
			// Wichtige Information f�r Adapter: Ist das Element ein RouletteItem?
			this.isRouletteItem = isRouletteItem;
		}catch(JSONException ex){
			Log.v("TopicItem - Konstruktor", "JSONException: " + ex.toString());
		}
	}
	
	//Eigene Topics (kommen nicht ins Roulette) 
	public TopicItem(JSONObject jsobject){
		Log.v("TopicItem Konstruktor-Object", "JSONObject:  " + jsobject.toString());
		try{
			// JSONObject auslesen
			this.setID(jsobject.getString("id"));
			this.title = jsobject.getString("titel");
			this.setUpdated(jsobject.getString("updated"));
			this.description = jsobject.getString("description");
			this.setTopicID(jsobject.getInt("topicID"));
			this.timestamp = jsobject.getString("date");	
			this.isRouletteItem = false;
		}catch(JSONException ex){
			Log.v("TopicItem - Konstruktor-Object", "JSONException: " + ex.toString());
		}
	}
	
	//Parcelable Konstrukt, damit Activitys einzelne TopicItems �bergeben werden k�nnen,
	//f�r anzeigen eines konkreten Topics notwendig.
	public static final Parcelable.Creator<TopicItem> CREATOR = 
			new Parcelable.Creator<TopicItem>() { 
		public TopicItem createFromParcel(Parcel in) { 
			return new TopicItem(in); 
		}   
		public TopicItem[] newArray(int size) { 
			return new TopicItem[size]; 
		} 
	};
	
	public TopicItem(Parcel in){
		readFromParcel(in);
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeInt(topicID);
		dest.writeByte((byte) (archived ? 1 : 0));
		dest.writeString(updated);
		dest.writeString(title);
		dest.writeString(description);
		dest.writeString(timestamp);
		dest.writeByte((byte) (isRouletteItem ? 1 : 0));
		dest.writeInt(authorID);
	}
	
	private void readFromParcel(Parcel in){
		id = in.readString();
		topicID = in.readInt();
		archived = in.readByte() != 0;
		updated = in.readString();
		title = in.readString();
		description = in.readString();
		timestamp = in.readString();
		isRouletteItem = in.readByte() != 0;
		authorID = in.readInt();
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

	public String getID() {
		return id;
	}

	public void setID(String string) {
		this.id = string;
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
