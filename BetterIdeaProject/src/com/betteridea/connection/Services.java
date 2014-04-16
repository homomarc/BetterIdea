package com.betteridea.connection;

import java.io.IOException;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.betteridea.logic.*;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.TextView;


public class Services extends Service {
	
	public static Services service = new Services();
	
	public static JSONObject userData = null;
	public static JSONArray rankList = null;
	public static JSONArray topicContent = null;
	
	static String name = null;
	static String mail = null;
	static String arr = null;
	static TextView text = null;
	public static boolean ready = false;

	// Erstellen eines neuen Accounts
	public void createUserData(String userName, String userMail) throws IOException, JSONException{
		name = userName;
		mail = userMail;
	    new Thread(new Runnable(){
	        public void run(){
	            try {
	        		String reqUrl = "http://space-labs.appspot.com/repo/2185003/ideas/services/insertUserData.sjs";
	        		String userID = Database.getRequest(reqUrl);
	        		java.util.Date now = new java.util.Date();
	        		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.GERMANY);
	        		String date = sdf.format(now);
	        		
	        		JSONObject json= new JSONObject();
	        		json.put("userID", userID);
	        		json.put("userName", name);
	        		json.put("mail", mail);
	        		json.put("date", date);
	        		json.put("credits", 250);
	        		json.put("score", 0);
	        		json.put("ideaCount", 0);
	        		json.put("topicCount", 0);
	        		json.put("spamCount", 0);
	        		
	        		userData = json;
	        		Database.putRequest(reqUrl, json);
	    		} catch (Exception e) {
	    			//TODO: Error-Message (Übertragung fehlgeschlagen.)
	    			e.printStackTrace();
	    		}            
	        }
	    }).start();
	}
	
	// Ändert den Usernamen eines Users
	static String username = "";
	public static void changeUsername(String newUserName) throws IOException{
		username = newUserName;
	    new Thread(new Runnable(){
	        public void run(){
	            try {
	            	userData.put("userName", username);
	            	String reqUrl = "http://space-labs.appspot.com/repo/2185003/ideas/api/users.sjs";
	            	Database.postRequest(reqUrl, userData);
	    		} catch (Exception e) {
	    			//TODO: Error-Message (Übertragung fehlgeschlagen.)
	    			e.printStackTrace();
	    		}            
	        }
	    }).start();
	}
	
	// Ändert die Credits eines Users
	static int credits = 0;
	public static void changeCredits(int change) throws IOException{
		credits = change;
	    new Thread(new Runnable(){
	        public void run(){
	            try {
	            	String creditString = userData.getString("credits");
	            	int creditState = Integer.valueOf(creditString);
	            	creditState += credits;
	            	userData.put("credits", creditState);
	            	String reqUrl = "http://space-labs.appspot.com/repo/2185003/ideas/services/userCredits.sjs";
	        		arr = Database.postRequest(reqUrl, userData);
	    		} catch (Exception e) {
	    			//TODO: Error-Message (Übertragung fehlgeschlagen.)
	    			e.printStackTrace();
	    		}            
	        }
	    }).start();
	}
	
	// Aktualisiert die Credits im User-Objekt
	public static void getCredits() throws IOException{
	    new Thread(new Runnable(){
	        public void run(){
	            try {
	            	int id = userData.getInt("id");
	        		String reqUrl = "http://space-labs.appspot.com/repo/2185003/ideas/services/userCredits.sjs?id=";
	        		reqUrl += id;
	        		arr = Database.getRequest(reqUrl);
	    			int length = arr.length();
	    			String arr1 = arr.substring(1, length-1);
	    			int credit = Integer.valueOf(arr1);
	    			userData.put("credits", credit);
	    		} catch (Exception e) {
	    			//TODO: Error-Message (Übertragung fehlgeschlagen.)
	    			e.printStackTrace();
	    		}
	        }
	    }).start();
	}

	// Erhöht den Spamcounter des Users um 1 und verringert die Credits bei einem Spamcount von 5
	public static void addSpam() throws IOException{
	    new Thread(new Runnable(){
	        public void run(){
	            try {
	        		String reqUrl = "http://space-labs.appspot.com/repo/2185003/ideas/services/insertUserData.sjs";
	            	String spamString = userData.getString("spamCount");
	            	int spamState = Integer.valueOf(spamString);
	            	spamState++;
	            	userData.put("spamCount", spamState);
	        		Database.postRequest(reqUrl, userData);
	        		if(spamState % 5 == 0){
	        			CreditSystem.spamComment();
	        		}
	    		} catch (Exception e) {
	    			//TODO: Error-Message (Übertragung fehlgeschlagen.)
	    			e.printStackTrace();
	    		}
	        }
	    }).start();
	}
	
	// Gibt alle Ideen zu einer Topic in das Array topicContent zurück
	static int topic = 0;
	public static void showTopic(int topicID) throws IOException{
		topic = topicID;
	    new Thread(new Runnable(){
	        public void run(){
	            try {
	            	String reqUrl = "http://space-labs.appspot.com/repo/2185003/ideas/services/topicIdeas.sjs?filter=topicID&value=";
	            	reqUrl += topic;
	            	String result = Database.getRequest(reqUrl);
	            	JSONArray resArray = new JSONArray(result);
	            	topicContent = resArray;
	    		} catch (Exception e) {
	    			//TODO: Error-Message (Übertragung fehlgeschlagen.)
	    			e.printStackTrace();
	    		}
	        }
	    }).start();
	}
	
	// Bezieht eine neue und zufällige Topic
	public static void getNewRandTopic() throws IOException{
	    new Thread(new Runnable(){
	        public void run(){
	            try {
	            	String reqUrl = "http://space-labs.appspot.com/repo/2185003/ideas/services/topicRoulette.sjs";
	            	String result = Database.getRequest(reqUrl);
	            	JSONObject jsObject = new JSONObject(result);
	            	TopicRoulette.setTopicCache(jsObject);
	    		} catch (Exception e) {
	    			//TODO: Error-Message (Übertragung fehlgeschlagen.)
	    			e.printStackTrace();
	    		}
	        }
	    }).start();
	}
	
	// Bezieht die Top-Rangliste 
	public static void getRankingList() throws IOException{
	    new Thread(new Runnable(){
	        public void run(){
	            try {
	        		String reqUrl = "http://space-labs.appspot.com/repo/2185003/ideas/services/rankList.sjs?sort=score";
	        		String result = Database.getRequest(reqUrl);
	        		String result2  = result.substring(1, result.length()-1);
	        		JSONArray jsArray = new JSONArray(result2);
	        		rankList = jsArray;
	    		} catch (Exception e) {
	    			//TODO: Error-Message (Übertragung fehlgeschlagen.)
	    			e.printStackTrace();
	    		}
	        }
	    }).start();
	}
	
	// Berechnet den Score des Users 
	public static void setUserScore() throws IOException{
	    new Thread(new Runnable(){
	        public void run(){
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
	    		} catch (Exception e) {
	    			//TODO: Error-Message (Übertragung fehlgeschlagen.)
	    			e.printStackTrace();
	    		}
	        }
	    }).start();
	}

	// Fügt dem IdeaConut des Users 1 hinzu 
	public static void addIdeaCount() throws IOException{
	    new Thread(new Runnable(){
	        public void run(){
	            try {
	            	String ideas = userData.getString("ideaCount");
	            	int ideaCount = Integer.valueOf(ideas);
	            	ideaCount++;
	            	userData.put("ideaCount", ideaCount);
	            	String reqUrl = "http://space-labs.appspot.com/repo/2185003/ideas/api/users.sjs";
	            	Database.postRequest(reqUrl, userData);
	    		} catch (Exception e) {
	    			//TODO: Error-Message (Übertragung fehlgeschlagen.)
	    			e.printStackTrace();
	    		}
	        }
	    }).start();
	}

	
	// Fügt dem TopicConut des Users 1 hinzu
	public static void addTopicCount() throws IOException{
	    new Thread(new Runnable(){
	        public void run(){
	            try {
	            	String topics = userData.getString("topicCount");
	            	int topicCount = Integer.valueOf(topics);
	            	topicCount++;
	            	userData.put("topicCount", topicCount);
	            	String reqUrl = "http://space-labs.appspot.com/repo/2185003/ideas/api/users.sjs";
	            	Database.postRequest(reqUrl, userData);
	    		} catch (Exception e) {
	    			//TODO: Error-Message (Übertragung fehlgeschlagen.)
	    			e.printStackTrace();
	    		}
	        }
	    }).start();
	}



	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
