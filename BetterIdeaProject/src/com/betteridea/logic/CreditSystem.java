package com.betteridea.logic;

public class CreditSystem {

	int startCredits = 250;
	int newIdea = -100;
	int pushIdea = -300;
	int goodComment = 50;
	int validComment = 10;
	int spamComments = -100;
	int showComment = -25;
	
	public void firstLogin(){
		com.betteridea.connection.Accounts.changeCredits(startCredits);
	}
	public void validComment(){
		com.betteridea.connection.Accounts.changeCredits(validComment);
	}
	public void goodComment(){
		com.betteridea.connection.Accounts.changeCredits(goodComment);
	}
	public void createIdea(){
		int credit = com.betteridea.connection.Accounts.getCredits();
		if(credit >= 100){
			com.betteridea.connection.Accounts.changeCredits(newIdea);
		}
		else{
			//TODO: Error-Message (zu wenig Credits)
		}
	}
	public void pushIdea(){
		int credit = com.betteridea.connection.Accounts.getCredits();
		if(credit >= 300){
			com.betteridea.connection.Accounts.changeCredits(pushIdea);
		}
		else{
			//TODO: Error-Message (zu wenig Credits)
		}
	}
	public void showComment(){
		int credit = com.betteridea.connection.Accounts.getCredits();
		if(credit >= 25){
			com.betteridea.connection.Accounts.changeCredits(showComment);
		}
		else{
			//TODO: Error-Message (zu wenig Credits)
		}
	}
	public void spamComment(){
			com.betteridea.connection.Accounts.changeCredits(spamComments);
	}

}
