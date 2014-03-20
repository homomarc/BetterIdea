package com.betteridea;

import org.json.JSONArray;

import android.os.AsyncTask;

public class DatabaseTest extends AsyncTask<String, String, String>{
	@Override
	protected String doInBackground(String... params) {
        String myInput = "";
        try {
    		String reqUrl = "http://space-labs.appspot.com/repo/2185003/ideas/api/idea.sjs";
    		String arr = com.betteridea.connection.Database.ideaInterface("get", "2", null, null, null, null, 0);
			myInput = arr.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return myInput;
	}
}
