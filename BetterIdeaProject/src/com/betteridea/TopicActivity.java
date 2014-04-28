package com.betteridea;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.betteridea.adapter.IdeaItemAdapter;
import com.betteridea.connection.Service;
import com.betteridea.connection.ServiceExecuter;
import com.betteridea.models.IdeaItem;
import com.betteridea.models.TopicItem;

public class TopicActivity extends Activity{
	private IdeaItemAdapter ideaItemAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.topic_overview_layout, null);
		
		Log.v("test","TopicActivity erreicht");
		
		Bundle bundle = getIntent().getExtras();
		TopicItem topicItem = bundle.getParcelable("com.betteridea.models.TopicItem");
		
		String jsonString;
		try {
			jsonString = new ServiceExecuter().execute("showTopic",""+topicItem.getTopicID()).get();
			JSONArray jsonArray = new JSONArray(jsonString);
			
			ideaItemAdapter = new IdeaItemAdapter(this, jsonArray, topicItem);
			
			ListView ideaList = (ListView) view.findViewById(R.id.list_topic_overview);
			
			ideaList.setAdapter(ideaItemAdapter);
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
	
	public void sendIdea(View view){
		EditText ideaText = (EditText) findViewById(R.id.text_enter_idea);
		String text = ideaText.getText().toString();
		Log.v("MainActivity (SendIdea)", "Idee: " + ideaText.getText().toString());
		java.util.Date now = new java.util.Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.GERMANY);
		String date = sdf.format(now);
		IdeaItem idea = null;
		try {
			idea = new IdeaItem(ideaText.getText().toString(),date,false,"",false,-1,-1,Service.userData.getString("userName"));
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(idea != null)
			ideaItemAdapter.addIdea(idea);
		
		//An Server senden
		String result = null;
		try {
			TopicItem topicItem = (TopicItem) ideaItemAdapter.getItem(0);
			result = new ServiceExecuter().execute("addIdea", text, topicItem.getTopicID()+"").get();
			ideaItemAdapter.notifyDataSetChanged();
			ideaText.setText("");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		if(result.equals("true")){
//			String result1 = new ServiceExecuter().execute("setScore").get();
//			if(result1.equals("true")){
//				Toast.makeText(this, "Idee eingereicht", Toast.LENGTH_SHORT).show();
//			}else{
//				Toast.makeText(this, "Übertragung fehlgeschlagen.", Toast.LENGTH_SHORT).show();
//			}
//		}else{
//			Toast.makeText(this, "Übertragung fehlgeschlagen.", Toast.LENGTH_SHORT).show();
//		}
	}
}
