package com.betteridea.logic;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.betteridea.connection.*;

public class TopicRoulette {
	
	public static TopicRoulette topics = new TopicRoulette();
	
	private static JSONArray topicCache = new JSONArray();
	private static int counter = 0;
	private static int arrayLength = 0; 
	
	public static void loadTopicCache() throws IOException{
		for(int i=0; i<5; i++){
			Services.getNewRandTopic();
		}

	}
	
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
			loadTopicCache();
		}
		return nextObject;
	}

	public static JSONArray getTopicCache() {
		return topicCache;
	}

	public static void setTopicCache(JSONObject topic) {
		TopicRoulette.topicCache.put(topic);
	}
	
}
