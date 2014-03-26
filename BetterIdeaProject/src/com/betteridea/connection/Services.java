package com.betteridea.connection;

import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.widget.TextView;

import com.betteridea.LoginActivity;
import com.betteridea.MainActivity;
import com.google.gson.JsonObject;

public class Services extends Activity {
	
	public static Services service = new Services();
	

	// need to be in separate thread
	public String insertUserData(String userName, String userMail) throws IOException, JSONException{
		String reqUrl = "http://space-labs.appspot.com/repo/2185003/ideas/services/insertUserData.sjs";
		
		String userID = Database.getRequest(reqUrl);
		
		java.util.Date now = new java.util.Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.GERMANY);
		String date = sdf.format(now);
		
		JsonObject json= new JsonObject();
		json.addProperty("userID", userID);
		json.addProperty("userName", userName);
		json.addProperty("mail", userMail);
		json.addProperty("date", date);
		json.addProperty("credits", 250);
		json.addProperty("score", 0);
		json.addProperty("ideaCount", 0);
		json.addProperty("topicCount", 0);
		
		String bool = Database.putRequest(reqUrl, json);
		return bool;
	}
	

	static String myInput = "BetterIdea Error";
	static String mail = null;
	static String reqUrl = null;
	static TextView text = null;
	
	public static void getUserData(String userMail, TextView myText) throws InterruptedException{
		mail = userMail;
		reqUrl = null;
		text = myText;
	    new Thread(new Runnable(){
	        public void run(){
	            try {
	        		reqUrl = "http://space-labs.appspot.com/repo/2185003/ideas/services/getUserData.sjs";
	        		reqUrl += "?mail=";
	        		reqUrl += mail;
	        		String arr = com.betteridea.connection.Database.getRequest(reqUrl);
	        		myInput = arr;
	    		} catch (Exception e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}
	            text.post(new Runnable(){
            	public void run(){
            		text.setText(myInput);
            	}	            
	            });	            
	        }
	    }).start();
	}
	
	static String userData = null;
	static Intent intent1  = null;
	static String arr = null;
	public void login(String userMail, Intent intent)throws InterruptedException{
		mail = userMail;
		intent1 = intent;
	    new Thread(new Runnable(){
	        public void run(){
	            try {
	        		reqUrl = "http://space-labs.appspot.com/repo/2185003/ideas/services/getUserData.sjs";
	        		reqUrl += "?mail=";
	        		reqUrl += mail;
	        		arr = com.betteridea.connection.Database.getRequest(reqUrl);
	        		if(arr != "false"){
	        			userData = arr;
	        		}
	        		
	    		} catch (Exception e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}
	            if(arr != "false"){
		            text.post(new Runnable(){
	            	public void run(){
	            		startActivity(intent1);
	            	}	            
		            });	
	            }
	        }
	    }).start();
	}

}
