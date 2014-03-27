package com.betteridea.connection;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

	public class Login extends AsyncTask<String, Integer, String>{
		@Override
		protected String doInBackground(String... mail) {
	        String myInput = null;
	        try {
        		String reqUrl = "http://space-labs.appspot.com/repo/2185003/ideas/services/getUserData.sjs";
        		reqUrl += "?mail=";
        		reqUrl += mail[0];
        		String arr = com.betteridea.connection.Database.getRequest(reqUrl);
        		String compare = "0";
        		if(!arr.equals(compare)){
        			formatUserData(arr);
        			myInput = arr;
        		}
	        } catch (Exception e) {
				e.printStackTrace();
			}
			return myInput;
		}
		
		static void formatUserData(String userObject) throws JSONException{
			JSONObject userObj = new JSONObject(userObject);
			Services.userData = userObj;	
			System.out.println("User Daten als Json: " + Services.userData);
		}
	}
	