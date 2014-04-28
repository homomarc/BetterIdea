package com.betteridea.fragments;

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
import com.betteridea.models.TopicItem;

public class HomeFragment extends Fragment {
	ListView topicList;
	
	public HomeFragment(){
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.v("test", "onCreate of HomeFragment");		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState){
		View view = inflater.inflate(R.layout.main_layout, container, false);
		System.out.println("Test oncreateVIEw");
		MainActivity mainActivity = (MainActivity) getActivity();
		TopicItemAdapter adapter = mainActivity.getTopicItemAdapter();
		
		Log.v("test", "onCreateView of HomeFragment");
		
		topicList = (ListView) view.findViewById(R.id.list_topic_feed);
		
		ArrayList<TopicItem> topicItems = new ArrayList<TopicItem>();
//		topicItems.add(new TopicItem("Übungen für Handballtraining","Ich suche Handballtrainingsübung zum Aufwärmen.","14:43, 01.04.2014",false));
		
		Log.v("HomeFragment","Topics erstellt");
		
		try{
			String jsonObjString = new ServiceExecuter().execute("newRandTopic").get();
			TopicItem rouletteItem = new TopicItem(new JSONObject(jsonObjString));
			adapter = new TopicItemAdapter(getActivity(), topicItems, rouletteItem);
		}catch(ExecutionException ex){
			Log.v("test", "WxexException: " + ex.toString());
		}catch (InterruptedException e) {
			Log.v("test","InterruptedException: " + e.toString());
		}catch(JSONException ex){
			Log.v("test", "JSONException: " + ex.toString());
		}
		
		mainActivity.setTopicItemAdapter(adapter);
		topicList.setAdapter(adapter);
		setRetainInstance(false);
		
		setHasOptionsMenu(true);
		return view;
	}
	
	 @Override
	  public void onResume() {
	     Log.v("test", "onResume of HomeFragment");
	     super.onResume();
	  }

	  @Override
	  public void onPause() {
		  Log.v("test", "onPause of HomeFragment");
	     super.onPause();
	  }
	
	@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
	}

}