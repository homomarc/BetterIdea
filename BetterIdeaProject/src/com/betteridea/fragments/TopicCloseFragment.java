package com.betteridea.fragments;

import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.betteridea.R;
import com.betteridea.ValuateIdeaDialog;
import com.betteridea.adapter.IdeaItemCloseAdapter;
import com.betteridea.connection.Service;
import com.betteridea.connection.ServiceExecuter;
import com.betteridea.models.IdeaItem;
import com.betteridea.models.TopicItem;

public class TopicCloseFragment extends Fragment{
 private TopicItem item;
 
 public TopicCloseFragment(TopicItem item){
 	this.item = item;
 }
  
 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState){
 	View view = inflater.inflate(R.layout.topic_close_overview_layout, container, false);

 	TextView textViewCredits = (TextView) view.findViewById(R.id.textViewCredits);
	try {
		textViewCredits.setText("Credits: " + Service.userData.getString("credits"));
	} catch (JSONException e) {
		Log.v("test", e.toString());
	}
 	
 	ListView ideaList = (ListView) view.findViewById(R.id.list_topic_overview);
 	TextView badgeCount = (TextView) view.findViewById(R.id.textBadgeCount);
 	
 	try{
 		String jsonString = new ServiceExecuter().execute("showTopic", new String(item.getTopicID()+"")).get();
 		JSONArray ideaArray = new JSONArray(jsonString);
 		
 		IdeaItemCloseAdapter adapter = new IdeaItemCloseAdapter(getActivity(),ideaArray,item); 		
 		ideaList.setAdapter(adapter);
 		
 		badgeCount.setText(""+adapter.getCountUncovered());
 		
 		ideaList.setOnItemClickListener(new OnItemClickListener() {
 			@Override
 			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
 				IdeaItemCloseAdapter adapter = (IdeaItemCloseAdapter) parent.getAdapter();
 				IdeaItem item = (IdeaItem) adapter.getItem(position);
 				if(!item.isUncovered()){
 					String success;
					try {
						success = new ServiceExecuter().execute("uncover",""+item.getId()).get();
	 					if(success.equals("true")){
	 						item.setUncovered(true);
	 						adapter.notifyDataSetChanged();
	 						TextView badgeCount = (TextView) getActivity().findViewById(R.id.textBadgeCount);
	 						badgeCount.setText(""+adapter.getCountUncovered());
//	 						view.getContext()
	 					}else
	 						Toast.makeText(getActivity(), "Es ist ein Fehler unterlaufen", Toast.LENGTH_SHORT);
					} catch (InterruptedException e) {
						Log.v("test", e.toString());
					} catch (ExecutionException e) {
						Log.v("test", e.toString());
					}
 				}else if(item.isUncovered() && !item.isValuated()){
 					ValuateIdeaDialog dialog = new ValuateIdeaDialog();
 					dialog.show(getFragmentManager(), "valuate");
 				}
 			}
		});
 	}catch(Exception ex){
 		Log.v("test", "AsynchronException: " + ex.toString());
 	}
 	
 	return view;
 }
}