package com.betteridea;

import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.betteridea.adapter.IdeaItemAdapter;
import com.betteridea.connection.ServiceExecuter;
import com.betteridea.models.TopicItem;

public class TopicActivity extends Activity{
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		LayoutInflater inflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.topic_overview_layout, null);
		
		Bundle bundle = getIntent().getExtras();
		TopicItem topicItem = bundle.getParcelable("com.betteridea.models.TopicItem");
		
		String jsonString;
		try {
			jsonString = new ServiceExecuter().execute("showTopic",""+topicItem.getTopicID()).get();
			JSONArray jsonArray = new JSONArray(jsonString);
			
			IdeaItemAdapter ideaAdapter = new IdeaItemAdapter(this, jsonArray, topicItem);
			
			ListView ideaList = (ListView) findViewById(R.id.list_topic_overview);
			
			ideaList.setAdapter(ideaAdapter);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			Log.v("test",e.toString());
		} catch (JSONException e) {
			Log.v("test",e.toString());
		}
		
		
		setContentView(view);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
	}
}
