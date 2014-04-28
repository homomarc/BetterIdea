package com.betteridea.logic;

/**
 * Author: 		Better Idea
 * Description:	TopicRoulette-Logik verwendet einen sogenannten TopicCache,
 * 				mithilfe dieses TopicCaches werden zunächst 5 Topics im Cache vorgehalten.
 * 				Der TopicCache wird dynamisch erweitert. Wenn TopicCachegröße-2 Topics angezeigt wurden,
 * 				werden fünf weitere Topics in den Cache geladen.
 * 
 * TODOS:		keine
 * 
 */

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.betteridea.connection.*;

public class TopicRoulette {
	
	public static TopicRoulette topics = new TopicRoulette();
	private static JSONArray topicCache = new JSONArray();
	private static int counter = 0;
	private static int arrayLength = 0;
	
	// TopicCache wird geladen (angestoßen beim Login)
	public static String loadTopicCache() throws IOException, InterruptedException, ExecutionException{
		String check = null;
		check = new ServiceExecuter().execute("newRandTopic").get();
		return check;
	}
	// Nächstes Topic ins Roulette aus dem Cache laden
	public static JSONObject getNextTopic() throws IOException, JSONException{
		boolean check = true;
		int try1 = 0;
		while(check){
			try {
				getTopicCache().getJSONObject(try1);
				try1++;
			} catch (Exception e) {
				check = false;
			}
		}
		arrayLength = try1;
		System.out.println("Array length: "+arrayLength);
		
		System.out.println("Counter: "+counter);
		JSONObject nextObject = getTopicCache().getJSONObject(counter);
		
		counter++;
		if(arrayLength - counter == 2){
			try {
				loadTopicCache();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		return nextObject;
	}

	public static JSONArray getTopicCache() {
		return topicCache;
	}

	public static void setTopicCache(JSONArray jsArr) throws JSONException {
		for(int i=0; i<5;i++){
			TopicRoulette.topicCache.put(jsArr.getJSONObject(i));	
		}
	}
	
}
