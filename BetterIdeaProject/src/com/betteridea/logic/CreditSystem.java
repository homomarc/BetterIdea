package com.betteridea.logic;

import java.io.IOException;

import org.json.JSONException;

import com.betteridea.connection.Services;

public class CreditSystem {

	int newIdea = -100;
	int pushIdea = -300;
	int goodComment = 50;
	int validComment = 10;
	int spamComments = -100;
	int showComment = -25;
	

	public void validComment() throws IOException{
		com.betteridea.connection.Services.changeCredits(validComment);
	}
	public void goodComment() throws IOException{
		com.betteridea.connection.Services.changeCredits(goodComment);
	}
	public void createIdea() throws IOException, JSONException{
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
	public void pushIdea() throws IOException, JSONException{
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
	public void showComment() throws IOException, JSONException{
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
	public void spamComment() throws IOException{
			com.betteridea.connection.Services.changeCredits(spamComments);
	}
}
