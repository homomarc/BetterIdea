package com.betteridea.fragments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

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
import com.betteridea.adapter.TopicItemAdapter;
import com.betteridea.connection.ServiceExecuter;
import com.betteridea.logic.TopicRoulette;
import com.betteridea.models.TopicItem;

public class HomeFragment extends Fragment {
	ListView topicList;
	//TODO:DELETE REFRESH SPERRE
	private boolean refreshed=false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		System.out.println("TEST oncreate");
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState){
		View view = inflater.inflate(R.layout.main_layout, container, false);
		System.out.println("Test oncreateVIEw");
		MainActivity mainActivity = (MainActivity) getActivity();
		TopicItemAdapter adapter = mainActivity.getTopicItemAdapter();
		
		topicList = (ListView) view.findViewById(R.id.list_topic_feed);
		
		ArrayList<TopicItem> topicItems = new ArrayList<TopicItem>();
//		topicItems.add(new TopicItem("�bungen f�r Handballtraining","Ich suche Handballtrainings�bung zum Aufw�rmen.","14:43, 01.04.2014",false));
		
		Log.v("HomeFragment","Topics erstellt");

		if(refreshed==false){
		try{
			String jsonObjString = new ServiceExecuter().execute("newRandTopic").get();
			TopicItem rouletteItem = new TopicItem(new JSONObject(jsonObjString));
			topicItems.add(rouletteItem);
		}catch(ExecutionException ex){
			Log.v("test", "WxexException: " + ex.toString());
		}catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Log.v("test","InterruptedException: " + e.toString());
		}catch(JSONException ex){
			Log.v("test", "JSONException: " + ex.toString());
		}
			refreshed = true;
		}
		adapter = new TopicItemAdapter(getActivity(), topicItems,new TopicItem("Thema 3","Wir haben die Aufgabe bekommen, eine native App f�r die Mobile Vorlesung zu entwickeln. Uns fehlen Ideen, welche M�glichkeiten es f�r Ideenapps gibt.","18:12, 01.04.2014",true));
		
		mainActivity.setTopicItemAdapter(adapter);
		topicList.setAdapter(adapter);
		
		setHasOptionsMenu(true);
		return view;
	}
	
	 @Override
	  public void onResume() {
	     Log.e("DEBUG", "onResume of HomeFragment");
	     super.onResume();
	  }

	  @Override
	  public void onPause() {
	     Log.e("DEBUG", "OnPause of HomeFragment");
	     super.onPause();
	  }
	
	@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
	}

}