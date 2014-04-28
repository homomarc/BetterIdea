package com.betteridea;

import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.betteridea.adapter.IdeaItemCloseAdapter;
import com.betteridea.connection.ServiceExecuter;
import com.betteridea.models.IdeaItem;
import com.betteridea.models.TopicItem;

public class TopicCloseActivity extends Activity{

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.topic_close_overview_layout, null);
		
		Bundle bundle = getIntent().getExtras();
		TopicItem topicItem = bundle.getParcelable("com.betteridea.models.TopicItem");
		
		try {
			String jsonString = new ServiceExecuter().execute("showTopic",""+topicItem.getTopicID()).get();
			JSONArray jsonArray = new JSONArray(jsonString);
			
			Log.v("test", "SelectedItem: " + topicItem.getID());
			IdeaItemCloseAdapter adapter = new IdeaItemCloseAdapter(this, jsonArray, topicItem);
			
			ListView ideaList = (ListView) view.findViewById(R.id.list_topic_overview);
			ideaList.setAdapter(adapter);
			
			ideaList.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id){
					if(position!=0){
						IdeaItemCloseAdapter adapter = (IdeaItemCloseAdapter) parent.getAdapter();
						IdeaItem item = (IdeaItem) adapter.getItem(position);
						
						if(!item.isUncovered()){
							String success;
							try {
								success = new ServiceExecuter().execute("uncover",""+item.getId()).get();
			 					if(success.equals("true")){
			 						item.setUncovered(true);
			 						adapter.notifyDataSetChanged();
			 						TextView badgeCount = (TextView) findViewById(R.id.textBadgeCount);
			 						badgeCount.setText(""+adapter.getCountUncovered());
//			 						view.getContext()
			 					}else{
//			 						Toast.makeText(view.getActivity(), "Es ist ein Fehler unterlaufen", Toast.LENGTH_SHORT);
			 					}
							} catch (InterruptedException e) {
								Log.v("test", e.toString());
							} catch (ExecutionException e) {
								Log.v("test", e.toString());
							}
						}else if(!item.isValuated()){
							
						}
					}
				}
			});
		} catch (InterruptedException e) {
			Log.v("test", e.toString());
		} catch (ExecutionException e) {
			Log.v("test", e.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		setContentView(view);
	}
	
	@Override
	public void onResume(){
		super.onResume();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		int id = item.getItemId();
		
		if(id == android.R.id.home){
			NavUtils.navigateUpFromSameTask(this);
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
}