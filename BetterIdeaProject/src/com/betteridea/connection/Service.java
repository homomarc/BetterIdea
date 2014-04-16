package com.betteridea.connection;

import java.io.IOException;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.betteridea.logic.CreditSystem;
import com.betteridea.logic.TopicRoulette;


public class Service {
	
	public static JSONObject userData = null;
	public static JSONArray rankList = null;
	public static JSONArray topicContent = null;
	
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
	public static String changeCredits(String data) throws IOException{
	            try {
	            	String creditString = userData.getString("credits");
	            	int creditState = Integer.valueOf(creditString);
	            	int change = Integer.valueOf(data);
	            	creditState += change;
	            	userData.put("credits", creditState);
	            	String reqUrl = "http://space-labs.appspot.com/repo/2185003/ideas/services/userCredits.sjs";
	        		Database.postRequest(reqUrl, userData);
	        		return "true";
	    		} catch (Exception e) {
	    			e.printStackTrace();
	    			return "false";
	    		}
	}
	
	// Aktualisiert die Credits im User-Objekt
	public static String getCredits() throws IOException{
	            try {
	            	int id = userData.getInt("id");
	        		String reqUrl = "http://space-labs.appspot.com/repo/2185003/ideas/services/userCredits.sjs?id=";
	        		reqUrl += id;
	        		String arr = Database.getRequest(reqUrl);
	    			int length = arr.length();
	    			String arr1 = arr.substring(1, length-1);
	    			int credit = Integer.valueOf(arr1);
	    			userData.put("credits", credit);
	    			return arr1;
	    		} catch (Exception e) {
	    			e.printStackTrace();
	    			return "false";
	    		}
	}

	// Erhöht den Spamcounter des Users um 1 und verringert die Credits bei einem Spamcount von 5
	public static String addSpam() throws IOException{
	            try {
	        		String reqUrl = "http://space-labs.appspot.com/repo/2185003/ideas/services/insertUserData.sjs";
	            	String spamString = userData.getString("spamCount");
	            	int spamState = Integer.valueOf(spamString);
	            	spamState++;
	            	userData.put("spamCount", spamState);
	        		Database.postRequest(reqUrl, userData);
	        		if(spamState % 5 == 0){
//	        			CreditSystem.spamComment();
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

	
	// Fügt dem TopicConut des Users 1 hinzu
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
}
