package com.betteridea.connection;

import java.io.IOException;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.TextView;


public class Services extends Service {
	
	public static Services service = new Services();
	
	public static JSONObject userData = null;
	
	static String name = null;
	static String mail = null;
	static String reqUrl = null;
	static String arr = null;
	static TextView text = null;
	

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
	
	// Evtl. für Einstellungen --> Accountdaten ändern
	public void getUserData(String userMail, TextView myText) throws InterruptedException{
		mail = userMail;
		text = myText;
	    new Thread(new Runnable(){
	        public void run(){
	            try {
	        		reqUrl = "http://space-labs.appspot.com/repo/2185003/ideas/services/getUserData.sjs";
	        		reqUrl += "?mail=";
	        		reqUrl += mail;
	        		arr = Database.getRequest(reqUrl);
	    		} catch (Exception e) {
	    			//TODO: Error-Message (Übertragung fehlgeschlagen.)
	    			e.printStackTrace();
	    		}
	            text.post(new Runnable(){
            	public void run(){
            		text.setText(arr);
            	}	            
	            });	            
	        }
	    }).start();
	}
	
	
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
	            	reqUrl = "http://space-labs.appspot.com/repo/2185003/ideas/services/userCredits.sjs";
	        		arr = Database.postRequest(reqUrl, userData);
	    		} catch (Exception e) {
	    			//TODO: Error-Message (Übertragung fehlgeschlagen.)
	    			e.printStackTrace();
	    		}            
	        }
	    }).start();
	}
	
	public static void getCredits() throws IOException{
	    new Thread(new Runnable(){
	        public void run(){
	            try {
	            	int id = userData.getInt("id");
	        		reqUrl = "http://space-labs.appspot.com/repo/2185003/ideas/services/userCredits.sjs?id=";
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


	public static void addSpam() throws IOException{
	    new Thread(new Runnable(){
	        public void run(){
	            try {
	        		String reqUrl = "http://space-labs.appspot.com/repo/2185003/ideas/services/insertUserData.sjs";
	            	String spamString = userData.getString("spamCount");
	            	int spamState = Integer.valueOf(spamString);
	            	spamState += 1;
	            	userData.put("spamCount", spamState);
	        		Database.postRequest(reqUrl, userData);
	    		} catch (Exception e) {
	    			//TODO: Error-Message (Übertragung fehlgeschlagen.)
	    			e.printStackTrace();
	    		}
	        }
	    }).start();
	}
	
	static int topic = 0;
	public static void showTopic(int topicID) throws IOException{
		topic = topicID;
	    new Thread(new Runnable(){
	        public void run(){
	            try {
	            	//TODO
	    		} catch (Exception e) {
	    			//TODO: Error-Message (Übertragung fehlgeschlagen.)
	    			e.printStackTrace();
	    		}
	        }
	    }).start();
	}
	





	@Override
	public IBinder onBind(Intent intent) {
		// TODO Bind a Service-Call to the specific Service
		return null;
	}

}
