package com.betteridea.connection;

import java.io.IOException;
import java.util.Locale;
import org.json.JSONException;
import com.google.gson.JsonObject;

public class Services {

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
	
	static String myInput = "";
	public static String getUserData(final String userMail){
	    new Thread(new Runnable() {
	        public void run() {
	            try {
	        		String reqUrl = "http://space-labs.appspot.com/repo/2185003/ideas/api/idea.sjs";
	        		String arr = com.betteridea.connection.Database.getRequest(reqUrl);
	    			myInput = arr;
	    			myInput += userMail;
	    		} catch (Exception e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		} 
	        }
	    }).start();
		return myInput;
	}

}
