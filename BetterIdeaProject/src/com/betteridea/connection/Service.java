package com.betteridea.connection;

import java.io.IOException;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.betteridea.logic.TopicRoulette;


public class Service {
	
	public static JSONObject userData = null;
	public static JSONArray rankList = null;
	public static JSONArray topicContent = null;
	public static JSONArray userTopics = null;
	
	// Neuen User anlegen
	public static String createUserData(String userName, String userMail) throws IOException, JSONException{
        try {
    		String reqUrl = "http://space-labs.appspot.com/repo/2185003/ideas/services/insertUserData.sjs";
    		String userID = Database.getRequest(reqUrl);
    		java.util.Date now = new java.util.Date();
    		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.GERMANY);
    		String date = sdf.format(now);
    		
    		JSONObject json= new JSONObject();
    		json.put("userID", userID);
    		json.put("userName", userName);
    		json.put("mail", userMail);
    		json.put("date", date);
    		json.put("credits", 250);
    		json.put("score", 0);
    		json.put("ideaCount", 0);
    		json.put("topicCount", 0);
    		json.put("spamCount", 0);      		
    		userData = json;
    		Database.putRequest(reqUrl, json);
    		return "true";
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}
	}
	
	// Ändert den Usernamen eines Users
	public static String changeUsername(String newUserName) throws IOException{
        try {
        	userData.put("userName", newUserName);
        	String reqUrl = "http://space-labs.appspot.com/repo/2185003/ideas/api/users.sjs";
        	Database.postRequest(reqUrl, userData);
        	return "true";
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}
       
	}
	
	// Ändert die Credits eines Users
	public static String changeCredits(String newCredits, String authorID) throws IOException{
        try {
        	if(authorID.equals("null")){
            	String creditString = userData.getString("credits");
            	int creditState = Integer.valueOf(creditString);
            	int change = Integer.valueOf(newCredits);
            	creditState += change;
            	userData.put("credits", creditState);
            	String reqUrl = "http://space-labs.appspot.com/repo/2185003/ideas/services/userCredits.sjs";
            	Database.postRequest(reqUrl, userData);
        		return "true";
        	}else{
        		String reqUrl = "http://space-labs.appspot.com/repo/2185003/ideas/api/users.sjs?filter=userID&value=";
        		reqUrl += authorID;
        		String jsonArr = Database.getRequest(reqUrl);
        		JSONArray jsArray = new JSONArray(jsonArr);
        		JSONObject json = jsArray.getJSONObject(0);
        		String credits = json.getString("credits");
            	int creditState = Integer.valueOf(credits);
            	int change = Integer.valueOf(newCredits);
            	creditState += change;
        		json.put("credits", creditState);
            	String reqUrl1 = "http://space-labs.appspot.com/repo/2185003/ideas/services/userCredits.sjs";
        		Database.postRequest(reqUrl1, json);
        		return "true";
        	}
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}
	}
	
	// Aktualisiert die Credits im User-Objekt
	public static String getCredits() throws IOException{
        try {
        	String id = userData.getString("id");
    		String reqUrl = "http://space-labs.appspot.com/repo/2185003/ideas/services/userCredits.sjs?id=";
    		reqUrl += id;
    		String userJson = Database.getRequest(reqUrl);
    		JSONObject json = new JSONObject(userJson);
    		String arr1 = json.getString("credits");
			userData.put("credits", arr1);
			return arr1;
		} catch (Exception e) {
			Log.v("test", "Exception in Service.getCredits(): " + e.toString());
			e.printStackTrace();
			return "-999";
		}
	}

	// Erhöht den Spamcounter des Users um 1 und verringert die Credits bei einem Spamcount von 5
	public static String addSpam(String newCredits, String authorID) throws IOException{
        try {
    		String reqUrl = "http://space-labs.appspot.com/repo/2185003/ideas/api/users.sjs?filter=userID&value=";
    		reqUrl += authorID;
    		String jsonArr = Database.getRequest(reqUrl);
    		JSONArray jsArray = new JSONArray(jsonArr);
    		JSONObject json = jsArray.getJSONObject(0);
    		String spamCount = json.getString("spamCount");
        	int spamState = Integer.valueOf(spamCount);
        	spamState++;
        	json.put("spamCount", spamState);
    		Database.postRequest(reqUrl, json);
    		if(spamState % 5 == 0){
        		String credits = json.getString("credits");
            	int creditState = Integer.valueOf(credits);
            	int change = Integer.valueOf(newCredits);
            	creditState += change;
            	json.put("credits", creditState);
            	Database.postRequest(reqUrl, json);
    		}
    		return "true";
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}
	}
	
	// Gibt alle Ideen zu einer Topic in das Array topicContent zurück
	public static String showTopic(String data) throws IOException{
        try {
        	String reqUrl = "http://space-labs.appspot.com/repo/2185003/ideas/services/topicIdeas.sjs?filter=topicID&value=";
        	reqUrl += data;
        	String result = Database.getRequest(reqUrl);
        	JSONArray resArray = new JSONArray(result);
        	topicContent = resArray;
        	return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}
	}
	
	// Bezieht eine neue und zufällige Topic
	public static String getNewRandTopic() throws IOException{
        try {
        	String reqUrl = "http://space-labs.appspot.com/repo/2185003/ideas/services/topicRoulette.sjs";
        	String result = Database.getRequest(reqUrl);
        	JSONObject jsObject = new JSONObject(result);
        	TopicRoulette.setTopicCache(jsObject);
        	return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}
	}
	
	// Bezieht die Top-Rangliste 
	public static String getRankingList() throws IOException{
        try {
    		String reqUrl = "http://space-labs.appspot.com/repo/2185003/ideas/services/rankList.sjs?sort=score";
    		String result = Database.getRequest(reqUrl);
    		String result2  = result.substring(1, result.length()-1);
    		JSONArray jsArray = new JSONArray(result2);
    		rankList = jsArray;
    		return result2;
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}
	}
	
	// Berechnet den Score des Users 
	public static String setUserScore() throws IOException{
        try {
        	String ideas = userData.getString("ideaCount");
        	String topics = userData.getString("topicCount");
        	int ideaCount = Integer.valueOf(ideas);
        	int topicCount = Integer.valueOf(topics);
        	int score = ideaCount*50;
        	score += topicCount*100;
        	userData.put("score", score);
        	String reqUrl = "http://space-labs.appspot.com/repo/2185003/ideas/api/users.sjs";
        	Database.postRequest(reqUrl, userData);
        	return "true";
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}
	}

	// Fügt dem IdeaConut des Users 1 hinzu 
	public static String addIdeaCount() throws IOException{
        try {
        	String ideas = userData.getString("ideaCount");
        	int ideaCount = Integer.valueOf(ideas);
        	ideaCount++;
        	userData.put("ideaCount", ideaCount);
        	String reqUrl = "http://space-labs.appspot.com/repo/2185003/ideas/api/users.sjs";
        	Database.postRequest(reqUrl, userData);
        	return "true";
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}
	}

	
	// Fügt dem TopicCount des Users 1 hinzu
	public static String addTopicCount() throws IOException{
        try {
        	String topics = userData.getString("topicCount");
        	int topicCount = Integer.valueOf(topics);
        	topicCount++;
        	userData.put("topicCount", topicCount);
        	String reqUrl = "http://space-labs.appspot.com/repo/2185003/ideas/api/users.sjs";
        	Database.postRequest(reqUrl, userData);
        	return "true";
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}
	}
	
	// Erstellt eine neue Topic
	public static String addTopic(String title, String descripction) throws IOException{
        try {
    		String reqUrl = "http://space-labs.appspot.com/repo/2185003/ideas/services/insertTopic.sjs";
    		String topicID = Database.getRequest(reqUrl);
    		
    		java.util.Date now = new java.util.Date();
    		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.GERMANY);
    		String date = sdf.format(now);
    		
    		JSONObject json = new JSONObject();
    		json.put("topicID", topicID);
    		json.put("authorID", userData.getString("userID"));
    		json.put("titel", title);
    		json.put("date", date);
    		json.put("updated", "");
    		json.put("description", descripction);
    		json.put("archived", "false");
    		Database.putRequest(reqUrl, json);
        	return "true";
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}
	}
	
	public static String addIdea(String text, String topicID) throws IOException{
        try {
    		String reqUrl = "http://space-labs.appspot.com/repo/2185003/ideas/services/insertIdea.sjs";
    		String ideaID = Database.getRequest(reqUrl);
    		
    		java.util.Date now = new java.util.Date();
    		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.GERMANY);
    		String date = sdf.format(now);
    		
    		JSONObject json = new JSONObject();
    		json.put("isValuated", "false");
    		json.put("text", text);
    		json.put("uncovered", "false");
			json.put("ideaID", ideaID);
    		json.put("topicID", topicID);
    		json.put("date", date);
    		json.put("authorID", userData.getString("userID"));
    		Database.putRequest(reqUrl, json);
        	return "true";
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}
	}
	
	// Gibt alle Ideen zu einer Topic in das Array topicContent zurück
	public static String allUserTopic() throws IOException{
        try {
        	String reqUrl = "http://space-labs.appspot.com/repo/2185003/ideas/api/topic.sjs?filter=authorID&value=";
        	reqUrl += userData.getString("userID");
        	String result = Database.getRequest(reqUrl);
        	JSONArray resArray = new JSONArray(result);
        	userTopics = resArray;
        	return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}
	}
	public static String getUserRank() throws IOException{
        try {
        	String reqUrl = "http://space-labs.appspot.com/repo/2185003/ideas/services/getUserRank.sjs?sort=score&id=";
        	reqUrl += userData.getString("userID");
        	String rank = Database.getRequest(reqUrl);
        	return rank;
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}
	}
	public static String uncoverIdea(String id) throws IOException{
        try {
        	String reqUrl = "http://space-labs.appspot.com/repo/2185003/ideas/api/idea.sjs?id=";
        	reqUrl += id;
        	String result = Database.getRequest(reqUrl);
        	JSONObject idea = new JSONObject(result);
        	idea.put("uncovered", "true");
        	Database.postRequest(reqUrl, idea);
        	return "true";
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}
	}
	public static String valuateIdea(String id) throws IOException{
        try {
        	String reqUrl = "http://space-labs.appspot.com/repo/2185003/ideas/api/idea.sjs?id=";
        	reqUrl += id;
        	String result = Database.getRequest(reqUrl);
        	JSONObject idea = new JSONObject(result);
        	idea.put("isValuated", "true");
        	Database.postRequest(reqUrl, idea);
        	return "true";
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}
	}
	
}
