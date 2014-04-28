package com.betteridea.logic;

/**
 * Author: 		Better Idea
 * Description:	CreditSystem enthält alle Parameter zur Bewertung der Credits
 * 				für die einzelnen Aktionen des Users.
 * 
 * TODOS:		keine
 * 
 */

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;

import android.util.Log;

import com.betteridea.connection.ServiceExecuter;

public class CreditSystem {
	
	// Erhalt von Credits auf eigenes Punktekonto
	private static int newTopic = -100; 
	private static int pushTopic = -300;
	private static int showIdea = -25;
	
	// Auf fremdes Punktekonto (Bewertung der Ideen)
	private static int goodIdea = 50; 
	private static int validIdea = 10;
	private static int spamIdea = -100;

	// Neues Topic erstellen
	public static String newTopic() throws IOException, JSONException, InterruptedException, ExecutionException{   
		String credits = new ServiceExecuter().execute("getCredits").get();
		Log.v("CreditSystem","Credits: " + credits);
    	int credit = Integer.valueOf(credits);
		if(credit >= newTopic*(-1)){
			String check = new ServiceExecuter().execute("changeCredits", String.valueOf(newTopic), "null").get();
			return check;
		}
		else{
			//zu wenig Credits vorhanden
			return "toLess";
		}
	}
	// Eigenes Topic pushen, bedeutet, dass Topic besser positioniert wird und 
	// somit mehr Ideeneinreichungen erhalten soll. 
	// TODO: Methode wird aktuell noch nicht verwendet. (Zukunftsplan)
	public static String pushTopic() throws IOException, JSONException, InterruptedException, ExecutionException{   
		String credits = new ServiceExecuter().execute("getCredits").get();
    	int credit = Integer.valueOf(credits);
		if(credit >= pushTopic*(-1)){
			String check = new ServiceExecuter().execute("changeCredits", String.valueOf(pushTopic), "null").get();
			return check;
		}
		else{
			//zu wenig Credits vorhanden
			return "toLess";
		}
	}
	public static String showIdea(String id) throws IOException, JSONException, InterruptedException, ExecutionException{   
		String credits = new ServiceExecuter().execute("getCredits").get();
    	int credit = Integer.valueOf(credits);
		if(credit >= showIdea*(-1)){
			String check1 = new ServiceExecuter().execute("uncover", id).get();
			String check = "false";
			if(check1.equals("true")){
				check = new ServiceExecuter().execute("changeCredits", String.valueOf(showIdea), "null").get();
			}
			return check;
		}
		else{
			//zu wenig Credits vorhanden
			return "toLess";
		}
	}
	
	// Ab hier: Credits auf fremdes Punktekonto / Bewertung der Ideen
	// Valide Idee
	public static String validIdea(String authorID, String id) throws IOException, InterruptedException, ExecutionException{
		String check1 = new ServiceExecuter().execute("valuate", id).get();
		String check = "false";
		if(check1.equals("true")){
			check = new ServiceExecuter().execute("changeCredits", String.valueOf(validIdea), authorID).get();
		}
		return check;
	}
	// Gute Idee
	public static String goodIdea(String authorID, String id) throws IOException, InterruptedException, ExecutionException{
		String check1 = new ServiceExecuter().execute("valuate", id).get();
		String check = "false";
		if(check1.equals("true")){
			check = new ServiceExecuter().execute("changeCredits", String.valueOf(goodIdea), authorID).get();
		}
		return check;
	}
	// Idee als Spam markieren
	public static String spamIdea(String authorID, String id) throws IOException, InterruptedException, ExecutionException{
		String check1 = new ServiceExecuter().execute("valuate", id).get();
		String check = "false";
		if(check1.equals("true")){
			check = new ServiceExecuter().execute("addSpam", String.valueOf(spamIdea), authorID).get();
		}
		return check;
	}
}