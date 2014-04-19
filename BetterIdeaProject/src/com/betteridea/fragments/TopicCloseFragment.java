package com.betteridea.fragments;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.betteridea.MainActivity;
import com.betteridea.R;
import com.betteridea.adapter.IdeaItemAdapter;
import com.betteridea.connection.Service;
import com.betteridea.connection.ServiceExecuter;
import com.betteridea.logic.CreditSystem;
import com.betteridea.models.TopicItem;

public class TopicCloseFragment extends Fragment{
 private static TopicItem item;
 private static JSONArray uncoveredArray = new JSONArray();
 private static JSONArray coveredArray = new JSONArray();
  
 public TopicCloseFragment(TopicItem item){
 	this.item = item;
 }
  
 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState){
 	View view = inflater.inflate(R.layout.topic_close_overview_layout, container, false);

 	TextView textViewCredits = (TextView) view.findViewById(R.id.textViewCredits);
	try {
		textViewCredits.setText("Credits: " + Service.userData.getString("credits"));
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
 	
 	ListView ideaList = (ListView) view.findViewById(R.id.list_topic_overview);
 	TextView badgeCount = (TextView) view.findViewById(R.id.textBadgeCount);
 	MainActivity mainActivity = (MainActivity) getActivity();
 	IdeaItemAdapter adapter = mainActivity.getIdeaItemAdapter();
 	
 	JSONArray jsonArray = null;
 	
 	try{
 		String jsonString = new ServiceExecuter().execute("showTopic", new String(item.getTopicID()+"")).get();
 		JSONArray ideaArray = new JSONArray(jsonString);

 		int counter = 0;
 	 	uncoveredArray = new JSONArray();
 	 	coveredArray = new JSONArray();
 	 	for(int i=0; i<ideaArray.length(); i++){
	 	 	 String check = ideaArray.getJSONObject(i).getString("uncovered");
	 	 	 if(check.equals("false")){
		 	 	 counter++;
		 	 	 coveredArray.put(ideaArray.getJSONObject(i));
	 	 	 }else{
	 	 		 uncoveredArray.put(ideaArray.getJSONObject(i));
	 	 	 }
 	 	}
 		
 	 	badgeCount.setText(""+counter);
 		
 		adapter = new IdeaItemAdapter(getActivity(),uncoveredArray,item);
 		ideaList.setAdapter(adapter);
 	}catch(Exception ex){
 		Log.v("test", "AsynchronException: " + ex.toString());
 	}
 	

 	mainActivity.setIdeaItemAdapter(adapter);
 	
 	return view;
 }

	public static void uncoverIdea(View v) {
		String result = "";
		try {
			if(coveredArray.length()>0){
				result = CreditSystem.showIdea(coveredArray.getJSONObject(0).getString("id"));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*true --> erfolgreich
		false --> Fehler
		toLess --> Zu wenig credits*/
		if(result.equals("false")){
			System.out.println("Fehler beim aufdecken");
			//Toast.makeText(this, "Fehler", Toast.LENGTH_SHORT).show();
		}
		if(result.equals("true")){
			System.out.println("Erfolgreich aufgedeckt");
			//Toast.makeText(this, "Fehler", Toast.LENGTH_SHORT).show();
			MainActivity.refreshTopicCloseFragment(item);
		}
		if(result.equals("toLess")){
			System.out.println("Zu wenig Credits");
			//Toast.makeText(this, "Fehler", Toast.LENGTH_SHORT).show();
		}
		
	}
	public static void closeTopic(View v) {
		String id = item.getID();
		try {
			System.out.println("Topic mit ID: " +id+" schlieﬂen");
			new ServiceExecuter().execute("closeTopic", id).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}