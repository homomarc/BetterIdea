package com.betteridea.fragments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
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

public class TopicOwnFragment extends Fragment {
	ListView topicList;

	@Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.v("Load saved Instance test","TopicOwnFragment");
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState){
		View view = inflater.inflate(R.layout.topic_own_fragment, container, false);
		
		MainActivity mainActivity = (MainActivity) getActivity();
		TopicItemAdapter adapter = mainActivity.getTopicItemAdapter();
		
		topicList = (ListView) view.findViewById(R.id.list_own_topics);
		
		ArrayList<TopicItem> topicItems = new ArrayList<TopicItem>();

		try{
			String jsonObjString = new ServiceExecuter().execute("allUserTopics").get();
			JSONArray jsArray = new JSONArray(jsonObjString);
			
			for(int i=0;i<jsArray.length();i++){
				TopicItem rouletteItem = new TopicItem(jsArray.getJSONObject(i), true);
				topicItems.add(rouletteItem);
			}
		}catch(ExecutionException ex){
			Log.v("test", "WxexException: " + ex.toString());
		}catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Log.v("test","InterruptedException: " + e.toString());
		}catch(JSONException ex){
			Log.v("test", "JSONException: " + ex.toString());
		}
		
		adapter = new TopicItemAdapter(getActivity(), topicItems);
		
//		mainActivity.setTopicItemAdapter(adapter);
		
		topicList.setAdapter(adapter);
		
		return view;
	}
	
	@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
	}

}