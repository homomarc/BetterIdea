package com.betteridea.connection;

import java.io.IOException;
import java.net.URL;

import org.json.JSONException;

public class Accounts {
	public static void connect() throws IOException, JSONException{
	URL url = new URL("http://space-labs.appspot.com/repo/2185003/ideas/api/idea.sjs");
	String query = url.getQuery();
	System.out.println(query);
	}
}
