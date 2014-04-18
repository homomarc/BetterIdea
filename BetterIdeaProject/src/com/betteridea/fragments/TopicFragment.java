package com.betteridea.fragments;

import org.json.JSONArray;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.betteridea.MainActivity;
import com.betteridea.R;
import com.betteridea.adapter.IdeaItemAdapter;
import com.betteridea.connection.ServiceExecuter;
import com.betteridea.models.TopicItem;

public class TopicFragment extends Fragment{
	 private TopicItem rouletteItem;
  
	 public TopicFragment(TopicItem rouletteItem){
	 	this.rouletteItem = rouletteItem;
	 }
	 
	 @Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState){
	 	View view = inflater.inflate(R.layout.topic_overview_layout, container, false);
	 	 
	 	ListView ideaList = (ListView) view.findViewById(R.id.list_topic_overview);
	 	
	 	MainActivity mainActivity = (MainActivity) getActivity();
	 	IdeaItemAdapter adapter = mainActivity.getIdeaItemAdapter();
	 	
	 	JSONArray jsonArray = null;
	 	
	 	try{
	 		String jsonString = new ServiceExecuter().execute("showTopic", new String(rouletteItem.getTopicID()+"")).get();
	 		jsonArray = new JSONArray(jsonString);
//	 		Log.v("test", "Asynchrones Ergebnis: " + jsonArray);
	 		adapter = new IdeaItemAdapter(getActivity(),jsonArray,rouletteItem);
	 		ideaList.setAdapter(adapter);
	 	}catch(Exception ex){
	 		Log.v("test", "AsynchronException: " + ex.toString());
	 	}
	 	
//	 	if(jsonArray!=null){
	 		
//	 	}
	 	
	 	//IdeaItemAdapter adapter = new IdeaItemAdapter(getActivity(),Service.topicContent,rouletteItem);
	 	//ideaList.setAdapter(adapter);
	 	mainActivity.setIdeaItemAdapter(adapter);
	 	
	 	setHasOptionsMenu(true);
	 	 
	 	return view;
	 }
	  
	 @Override
	 public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
	 	inflater.inflate(R.menu.topic, menu);
	 }
}