package com.betteridea.models;

/**
 * Author: 		Better Idea
 * Description:	Modelklasse für die Datenhaltung der einzelnen IdeaItems,
 * 				notwendig zur Anzeige für den IdeaItemAdapter.
 * 
 * TODOS:		keine
 * 
 */

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class IdeaItem extends Item{
	private String text = "";
	private String date = "";
	private boolean isValuated = false;
	private String id = "";
	private boolean uncovered = false;
	private int ideaID = -1;
	private int topicID = -1;
	private String authorID = "-1";
	
	//IdeaItem Konstruktor mit allen Parametern
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
	//IdeaItem Konstruktor mit JSONObject
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
	//Parcelable Konstrukt, damit Activitys einzelne IdeaItems übergeben werden können,
	//für anzeigen einer konkreten Idee notwendig.
	public IdeaItem(Parcel in){
		readFromParcel(in);
	}
	
	public static final Parcelable.Creator<IdeaItem> CREATOR =
			new Parcelable.Creator<IdeaItem>() {
				public IdeaItem createFromParcel(Parcel in){
					return new IdeaItem(in);
				}
				public IdeaItem[] newArray(int size){
					return new IdeaItem[size];
				}
			};
	
	@Override 
	public int describeContents() { 
		return 0; 
	}
			
	@Override
	public void writeToParcel(Parcel dest, int flags){
		dest.writeString(text);
		dest.writeString(date);
		dest.writeByte((byte)(isValuated ? 1 : 0));
		dest.writeString(id);
		dest.writeByte((byte)(uncovered ? 1 : 0));
		dest.writeInt(ideaID);
		dest.writeInt(topicID);
		dest.writeString(authorID);
	}
	
	private void readFromParcel(Parcel in){
		text = in.readString();
		date = in.readString();
		isValuated = in.readByte() != 0;
		id = in.readString();
		uncovered = in.readByte() != 0;
		ideaID = in.readInt();
		topicID = in.readInt();
		authorID = in.readString();
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