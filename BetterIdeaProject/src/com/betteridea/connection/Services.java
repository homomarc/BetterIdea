package com.betteridea.connection;

import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.TextView;

import com.betteridea.LoginActivity;
import com.betteridea.MainActivity;
import com.betteridea.RegisterActivity;
import com.google.gson.JsonObject;

import com.betteridea.*;

public class Services extends Service {
	
	public static Services service = new Services();
	
	static JSONObject userData = null;
	
	static String mail = null;
	static String reqUrl = null;
	static TextView text = null;
	static String arr = null;
	
	

	// seperater thread
	public String insertUserData(String userName, String userMail) throws IOException, JSONException{
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
		
		String bool = Database.putRequest(reqUrl, json);
		return bool;
	}
	
	// Evtl. für Einstellungen --> Accountdaten ändern
	public void getUserData(String userMail, TextView myText) throws InterruptedException{
		mail = userMail;
		reqUrl = null;
		text = myText;
	    new Thread(new Runnable(){
	        public void run(){
	            try {
	        		reqUrl = "http://space-labs.appspot.com/repo/2185003/ideas/services/getUserData.sjs";
	        		reqUrl += "?mail=";
	        		reqUrl += mail;
	        		arr = com.betteridea.connection.Database.getRequest(reqUrl);
	    		} catch (Exception e) {
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
	        		reqUrl = "http://space-labs.appspot.com/repo/2185003/ideas/services/userCredits.sjs";

	        		arr = com.betteridea.connection.Database.postRequest(reqUrl, userData);
	    		} catch (Exception e) {
	    			e.printStackTrace();
	    		}            
	        }
	    }).start();
	}
	
	public static void getCredits() throws IOException{
	    new Thread(new Runnable(){
	        public void run(){
	            try {
	        		reqUrl = "http://space-labs.appspot.com/repo/2185003/ideas/services/userCredits.sjs";
	        		arr = com.betteridea.connection.Database.getRequest(reqUrl);
	    		} catch (Exception e) {
	    			e.printStackTrace();
	    		}            
	        }
	    }).start();
	}



	





	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
