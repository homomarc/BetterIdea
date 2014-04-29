package com.betteridea;

import java.io.IOException;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.betteridea.ValuateIdeaDialog.DialogResult;
import com.betteridea.adapter.IdeaItemCloseAdapter;
import com.betteridea.connection.Service;
import com.betteridea.connection.ServiceExecuter;
import com.betteridea.logic.CreditSystem;
import com.betteridea.models.IdeaItem;
import com.betteridea.models.TopicItem;

public class TopicCloseActivity extends Activity {
	private IdeaItemCloseAdapter ideaItemAdapter;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.topic_close_overview_layout, null);
		
		Bundle bundle = getIntent().getExtras();
		TopicItem topicItem = bundle.getParcelable("com.betteridea.models.TopicItem");
		
		try {
			String userCredits = Service.userData.getString("credits");
			TextView textCredits = (TextView) view.findViewById(R.id.textViewCredits);
			textCredits.setText("Credits: " + userCredits);
		} catch (JSONException e1) {
			Log.v("test", e1.toString());
		}
		
		try {
			String jsonString = new ServiceExecuter().execute("showTopic",""+topicItem.getTopicID()).get();
			JSONArray jsonArray = new JSONArray(jsonString);
			
			Log.v("test", "SelectedItem: " + topicItem.getID());
			ideaItemAdapter = new IdeaItemCloseAdapter(this, jsonArray, topicItem);
			
			ListView ideaList = (ListView) view.findViewById(R.id.list_topic_overview);
			ideaList.setAdapter(ideaItemAdapter);
		} catch (InterruptedException e) {
			Log.v("test", e.toString());
		} catch (ExecutionException e) {
			Log.v("test", e.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		TextView badgeCount = (TextView) view.findViewById(R.id.textBadgeCount);
		badgeCount.setText(""+ideaItemAdapter.getCountCovered());
		
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
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onBackPressed(){
		
	}
	
	public void uncoverIdea(View view){
		if(ideaItemAdapter.getCountCovered() > 0){
			if(ideaItemAdapter.toBeValuated() != null){
				ValuateIdeaDialog dialog = new ValuateIdeaDialog();
				dialog.show(getFragmentManager(), "valuate");
				dialog.setDialogResult(new DialogResult(){
					public void finish(String result){
						Log.v("test", "Ergebnis aus Dialog: " + result);
						if(result.equals("good")){
							try {
								IdeaItem item = ideaItemAdapter.toBeValuated();
								String success = CreditSystem.goodIdea(item.getAuthorID(), item.getId());
								if(success.equals("true")){
									item.setValuated(true);
								}else{
									Log.v("test", "Spam: false");
								}
							} catch (IOException e) {
								Log.v("test", e.toString());
							} catch (InterruptedException e) {
								Log.v("test", e.toString());
							} catch (ExecutionException e) {
								Log.v("test", e.toString());
							}
							
						}else if(result.equals("valid")){
							
							try {
								IdeaItem item = ideaItemAdapter.toBeValuated();
								String success = CreditSystem.validIdea(item.getAuthorID(), item.getId());
								if(success.equals("true")){
									item.setValuated(true);
								}else{
									Log.v("test", "Spam: false");
								}
							} catch (IOException e) {
								Log.v("test", e.toString());
							} catch (InterruptedException e) {
								Log.v("test", e.toString());
							} catch (ExecutionException e) {
								Log.v("test", e.toString());
							}
							
						}else if(result.equals("spam")){
							try {
								IdeaItem item = ideaItemAdapter.toBeValuated();
								String success = CreditSystem.spamIdea(item.getAuthorID(), item.getId());
								if(success.equals("true")){
									item.setValuated(true);
								}else{
									Log.v("test", "Spam: false");
								}
							} catch (IOException e) {
								Log.v("test", e.toString());
							} catch (InterruptedException e) {
								Log.v("test", e.toString());
							} catch (ExecutionException e) {
								Log.v("test", e.toString());
							}
							
						}
					}
				});
			}
			
			IdeaItem ideaItem = (IdeaItem) ideaItemAdapter.getCoveredIdea();
			try {
				boolean success = ideaItem.setUncovered(true);
				if(success){
					ideaItemAdapter.notifyDataSetChanged();
					TextView badgeCount = (TextView) findViewById(R.id.textBadgeCount);
					badgeCount.setText(ideaItemAdapter.getCountCovered()+"");
				}
			} catch(Exception ex){
				Log.v("test", ex.toString());
				Toast.makeText(this, "Es ist ein technischer Fehler unterlaufen", Toast.LENGTH_SHORT);
			}
		}else{
			Toast.makeText(this, "Keine weiteren verdeckten Ideen", Toast.LENGTH_SHORT);
		}
	}
}