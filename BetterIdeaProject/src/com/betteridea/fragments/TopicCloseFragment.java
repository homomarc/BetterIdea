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
import android.widget.TextView;

import com.betteridea.MainActivity;
import com.betteridea.R;
import com.betteridea.adapter.IdeaItemAdapter;
import com.betteridea.connection.ServiceExecuter;
import com.betteridea.models.TopicItem;

public class TopicCloseFragment extends Fragment{
 private TopicItem item;
  
 public TopicCloseFragment(TopicItem item){
 	this.item = item;
 }
  
 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState){
 	View view = inflater.inflate(R.layout.topic_close_overview_layout, container, false);
 	 
 	ListView ideaList = (ListView) view.findViewById(R.id.list_topic_overview);
 	TextView badgeCount = (TextView) view.findViewById(R.id.textBadgeCount);
 	MainActivity mainActivity = (MainActivity) getActivity();
 	IdeaItemAdapter adapter = mainActivity.getIdeaItemAdapter();
 	
 	JSONArray jsonArray = null;
 	
 	try{
 		String jsonString = new ServiceExecuter().execute("showTopic", new String(item.getTopicID()+"")).get();
 		jsonArray = new JSONArray(jsonString);
 		adapter = new IdeaItemAdapter(getActivity(),jsonArray,item);
 		ideaList.setAdapter(adapter);
 	}catch(Exception ex){
 		Log.v("test", "AsynchronException: " + ex.toString());
 	}
 	

 	mainActivity.setIdeaItemAdapter(adapter);
 	
 	return view;
 }
  
}