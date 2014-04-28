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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.app.FragmentManager;

import com.betteridea.MainActivity;
import com.betteridea.R;
import android.widget.AdapterView.OnItemClickListener;
import com.betteridea.adapter.TopicItemAdapter;
import com.betteridea.connection.ServiceExecuter;
import com.betteridea.logic.TopicRoulette;
import com.betteridea.models.TopicItem;

public class TopicOwnFragment extends Fragment {
	ListView topicList;
	
	public TopicOwnFragment(){
		
	}
	
	@Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.v("Load saved Instance test","TopicOwnFragment");
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState){
		View view = inflater.inflate(R.layout.topic_own_fragment, container, false);
		
		MainActivity mainActivity = (MainActivity) getActivity();
		
		topicList = (ListView) view.findViewById(R.id.list_own_topics);
		
		ArrayList<TopicItem> topicItems = new ArrayList<TopicItem>();

		try{
			String jsonObjString = new ServiceExecuter().execute("allUserTopics").get();
			JSONArray jsArray = new JSONArray(jsonObjString);
			TopicItemAdapter topicAdapter = new TopicItemAdapter(getActivity(),jsArray);
			topicAdapter.setOwnTopics(true);
			
			topicList.setAdapter(topicAdapter);
			//Keine eigenen Themen vorhanden?
			if(jsArray.length()<1){
				TextView textViewInfo = (TextView) view.findViewById(R.id.textViewInfo);
				textViewInfo.setText("Du hast noch keine Themen erstellt.");
			}
			
			//Eigene Themen hinzufügen
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
		
		topicList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				TopicItemAdapter tia = (TopicItemAdapter) parent.getAdapter();
				TopicItem selectedTopicItem = (TopicItem) tia.getItem(position);
				

				Fragment fragment = new TopicCloseFragment(selectedTopicItem);
				FragmentManager fragmentManager = getFragmentManager();
				fragmentManager.beginTransaction()
		    	.replace(R.id.content_frame,fragment)
		    	.commit();
			}});
		
		
		return view;
	}
	
	@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
	}

}