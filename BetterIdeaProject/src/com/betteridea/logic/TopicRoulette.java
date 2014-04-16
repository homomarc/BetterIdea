package com.betteridea.logic;

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
	
	public static String loadTopicCache() throws IOException, InterruptedException, ExecutionException{
		String check = null;
		for(int i=0; i<5; i++){
			check = new ServiceExecuter().execute("newRandTopic").get();
		}
		return check;
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
			try {
				loadTopicCache();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
