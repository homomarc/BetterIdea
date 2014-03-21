package com.betteridea;

import android.os.AsyncTask;

public class DatabaseTest extends AsyncTask<String, String, String>{
	@Override
	protected String doInBackground(String... params) {
        String myInput = "";
        try {
    		String reqUrl = "http://space-labs.appspot.com/repo/2185003/ideas/api/idea.sjs";
    		String arr = com.betteridea.connection.Database.getRequest(reqUrl);
			myInput = arr;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return myInput;
	}
}
