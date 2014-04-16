package com.betteridea.logic;

import java.io.IOException;

import org.json.JSONException;

import com.betteridea.connection.Services;

public class CreditSystem {

	private static int newIdea = -100;
	private static int pushIdea = -300;
	private static int goodComment = 50;
	private static int validComment = 10;
	private static int spamComments = -100;
	private static int showComment = -25;
	

	public static void validComment() throws IOException{
		com.betteridea.connection.Services.changeCredits(validComment);
	}
	public static void goodComment() throws IOException{
		com.betteridea.connection.Services.changeCredits(goodComment);
	}
	public static void createIdea() throws IOException, JSONException{
		com.betteridea.connection.Services.getCredits();
    	String creditString = Services.userData.getString("credits");
    	int credit = Integer.valueOf(creditString);
		if(credit >= 100){
			com.betteridea.connection.Services.changeCredits(newIdea);
		}
		else{
			//TODO: Error-Message (zu wenig Credits)
		}
	}
	public static void pushIdea() throws IOException, JSONException{
		com.betteridea.connection.Services.getCredits();
    	String creditString = Services.userData.getString("credits");
    	int credit = Integer.valueOf(creditString);
		if(credit >= 300){
			com.betteridea.connection.Services.changeCredits(pushIdea);
		}
		else{
			//TODO: Error-Message (zu wenig Credits)
		}
	}
	public static void showComment() throws IOException, JSONException{
		com.betteridea.connection.Services.getCredits();
    	String creditString = Services.userData.getString("credits");
    	int credit = Integer.valueOf(creditString);
		if(credit >= 25){
			com.betteridea.connection.Services.changeCredits(showComment);
		}
		else{
			//TODO: Error-Message (zu wenig Credits)
		}
	}
	public static void spamComment() throws IOException{
			com.betteridea.connection.Services.changeCredits(spamComments);
	}
}
