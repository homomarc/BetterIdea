package com.betteridea.logic;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;

import com.betteridea.connection.ServiceExecuter;

public class CreditSystem {
	
	// Auf eigenen Credits
	private static int newTopic = -100; 
	private static int pushTopic = -300;
	private static int showIdea = -25;
	
	// Auf fremden Credits (Bewertung der Ideen)
	private static int goodIdea = 50; 
	private static int validIdea = 10;
	private static int spamIdea = -100;

	public static String newTopic() throws IOException, JSONException, InterruptedException, ExecutionException{   
		String credits = new ServiceExecuter().execute("getCredits").get();
    	int credit = Integer.valueOf(credits);
		if(credit >= newTopic*(-1)){
			String check = new ServiceExecuter().execute("changeCredits", String.valueOf(newTopic), null).get();
			return check;
		}
		else{
			return "toLess";
		}
	}
	public static String pushTopic() throws IOException, JSONException, InterruptedException, ExecutionException{   
		String credits = new ServiceExecuter().execute("getCredits").get();
    	int credit = Integer.valueOf(credits);
		if(credit >= pushTopic*(-1)){
			String check = new ServiceExecuter().execute("changeCredits", String.valueOf(pushTopic), null).get();
			return check;
		}
		else{
			return "toLess";
		}
	}
	public static String showIdea() throws IOException, JSONException, InterruptedException, ExecutionException{   
		String credits = new ServiceExecuter().execute("getCredits").get();
    	int credit = Integer.valueOf(credits);
		if(credit >= showIdea*(-1)){
			String check = new ServiceExecuter().execute("changeCredits", String.valueOf(showIdea), null).get();
			return check;
		}
		else{
			return "toLess";
		}
	}
	
	// Ab hier: Credits auf fremdem Account ändern
	
	public static String validIdea(String authorID) throws IOException, InterruptedException, ExecutionException{
		String check = new ServiceExecuter().execute("changeCredits", String.valueOf(validIdea), authorID).get();
		return check;
	}
	public static String goodIdea(String authorID) throws IOException, InterruptedException, ExecutionException{
		String check = new ServiceExecuter().execute("changeCredits", String.valueOf(goodIdea), authorID).get();
		return check;
	}
	public static String spamIdea(String authorID) throws IOException, InterruptedException, ExecutionException{
		String check = new ServiceExecuter().execute("addSpam", String.valueOf(spamIdea), authorID).get();
		return check;
	}
}
