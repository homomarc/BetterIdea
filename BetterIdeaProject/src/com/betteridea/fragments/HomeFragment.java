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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState){
		View view = inflater.inflate(R.layout.main_layout, container, false);
		
		MainActivity mainActivity = (MainActivity) getActivity();
		TopicItemAdapter adapter = mainActivity.getTopicItemAdapter();
		
		topicList = (ListView) view.findViewById(R.id.list_topic_feed);
		
		ArrayList<TopicItem> topicItems = new ArrayList<TopicItem>();
//		topicItems.add(new TopicItem("Übungen für Handballtraining","Ich suche Handballtrainingsübung zum Aufwärmen.","14:43, 01.04.2014",false));
//		topicItems.add(new TopicItem("Ideenmanagementapp für die Mobile Vorlesung entwickeln","Wir haben die Aufgabe bekommen, eine native App für die Mobile Vorlesung zu entwickeln. Uns fehlen Ideen, welche Möglichkeiten es für Ideenapps gibt.","18:12, 01.04.2014",false));
//		topicItems.add(new TopicItem("Thema 3","Wir haben die Aufgabe bekommen, eine native App für die Mobile Vorlesung zu entwickeln. Uns fehlen Ideen, welche Möglichkeiten es für Ideenapps gibt.","18:12, 01.04.2014",false));
		
		Log.v("test","Topics erstellt");
		
//		TopicItem topicRouletteItem = null;
		try{
			TopicRoulette.loadTopicCache();
			String jsonObjString = new ServiceExecuter().execute("newRandTopic").get();
			TopicItem rouletteItem = new TopicItem(new JSONObject(jsonObjString));
			topicItems.add(rouletteItem);
		}catch(IOException ex){
			Log.v("test", "IOException: " + ex.toString());
		}catch(ExecutionException ex){
			Log.v("test", "WxexException: " + ex.toString());
		}catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Log.v("test","InterruptedException: " + e.toString());
		}catch(JSONException ex){
			Log.v("test", "JSONException: " + ex.toString());
		}
//			Log.v("test","loadTopicCache() durch");
//			topicRouletteItem = new TopicItem(TopicRoulette.getNextTopic());
//			Log.v("test", "Roulette Item erstellt");
//		}catch(IOException ex){
//			Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_SHORT);
//			Log.v("test", "IOException: " + ex.toString());
//		}catch(JSONException ex){
//			Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_SHORT);
//			Log.v("test", "JSONException: " + ex.toString());
//		}
//		TopicRoulette.getNextTopic() -> JSONObject
		
		//TopicItem topicRouletteItem = new TopicItem("Ideenmanagementapp für die Mobile Vorlesung entwickeln","Wir haben die Aufgabe bekommen, eine native App für die Mobile Vorlesung zu entwickeln. Uns fehlen Ideen, welche Möglichkeiten es für Ideenapps gibt.","",true);

//		if(topicRouletteItem != null)
//			adapter = new TopicItemAdapter(getActivity().getApplicationContext(), topicItems, topicRouletteItem);
		
		adapter = new TopicItemAdapter(getActivity(), topicItems,new TopicItem("Thema 3","Wir haben die Aufgabe bekommen, eine native App für die Mobile Vorlesung zu entwickeln. Uns fehlen Ideen, welche Möglichkeiten es für Ideenapps gibt.","18:12, 01.04.2014",true));
		
		mainActivity.setTopicItemAdapter(adapter);
		
		topicList.setAdapter(adapter);
		
		setHasOptionsMenu(true);
		
		return view;
	}
	
	@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
	}

}