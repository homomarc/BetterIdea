package com.betteridea;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.betteridea.adapter.NavDrawerListAdapter;
import com.betteridea.adapter.TopicItemAdapter;
import com.betteridea.connection.Login;
import com.betteridea.connection.ServiceExecuter;
import com.betteridea.fragments.HomeFragment;
import com.betteridea.fragments.SettingsFragment;
import com.betteridea.fragments.TopicFragment;
import com.betteridea.logic.CreditSystem;
import com.betteridea.logic.TopicRoulette;
import com.betteridea.models.NavDrawerItem;
import com.betteridea.models.TopicItem;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;

@SuppressLint("NewApi")
public class MainActivity extends Activity {
	private DrawerLayout drawerLayout;
	private ListView navigationList;
	private FragmentManager fragmentManager;
	private ActionBarDrawerToggle drawerToggle;
	
//	NavigationDrawer Items (Icons, Entries)
	private String[] navigationEntries;
	private TypedArray navigationIcons;
	
//	NavigationItem
	private ArrayList<NavDrawerItem> navigationItems;
	
//	Navigation Adapter
	private NavDrawerListAdapter adapter;
	private TopicItemAdapter topicItemAdapter;
	
	private GoogleApiClient mGoogleApiClient;
	
	private boolean menuIsInitialized = false;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        setContentView(R.layout.drawer_layout);
        
        Fragment fragment = new HomeFragment();
//        Fragment fragment = new TopicFragment();
        fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
        	.add(R.id.content_frame,fragment)
        	.commit();
        
//        Load navigation entries
        navigationEntries = getResources().getStringArray(R.array.navigation_entries);
 
//        Load navigation icons
        navigationIcons = getResources().obtainTypedArray(R.array.navigation_icons);
        
//        Instantiate drawerLayout and navigationList
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationList = (ListView) findViewById(R.id.left_drawer);        
        
//        Instantiate and fill drawerItems (consisting of icon and text)
        navigationItems = new ArrayList<NavDrawerItem>();
        navigationItems.add(new NavDrawerItem(navigationEntries[0],navigationIcons.getResourceId(0,-1)));
        navigationItems.add(new NavDrawerItem(navigationEntries[1],navigationIcons.getResourceId(1,-1)));
        navigationItems.add(new NavDrawerItem(navigationEntries[2],navigationIcons.getResourceId(2,-1)));
        navigationItems.add(new NavDrawerItem(navigationEntries[3],navigationIcons.getResourceId(3,-1)));
        navigationItems.add(new NavDrawerItem(navigationEntries[4],navigationIcons.getResourceId(4,-1)));
        
        adapter = new NavDrawerListAdapter(getApplicationContext(), navigationItems);
        
        navigationList.setAdapter(adapter);
        navigationList.setItemChecked(0,true);
        navigationList.setOnItemClickListener(new NavigationItemClickListener());
        
        drawerToggle = new ActionBarDrawerToggle(this, 
        		drawerLayout, 
        		R.drawable.ic_drawer,
        		R.string.drawer_open,
        		R.string.drawer_close){
        	public void onDrawerClosed(View view){
        		super.onDrawerClosed(view);
        		invalidateOptionsMenu();
        	}
        	
        	public void onDrawerOpened(View view){
        		super.onDrawerOpened(view);
        		invalidateOptionsMenu();
        	}
        };
        
        drawerLayout.setDrawerListener(drawerToggle);
        
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
    }
    
    @Override
    protected void onPostCreate(Bundle savedInstanceState){
    	super.onPostCreate(savedInstanceState);
    	drawerToggle.syncState();
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig){
    	super.onConfigurationChanged(newConfig);
    	drawerToggle.onConfigurationChanged(newConfig);
    }
    
    private class NavigationItemClickListener implements ListView.OnItemClickListener{

		@Override
		public void onItemClick(AdapterView parent, View view, int position,
				long id) {
			selectItem(position);
		}
		
		private void selectItem(int position){
			Fragment fragment = new HomeFragment();
			
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment)
					.commit();
			
			navigationList.setItemChecked(position, true);
			getActionBar().setTitle(navigationEntries[position]);
			drawerLayout.closeDrawer(navigationList);
			Intent intent = new Intent();
			switch(position){
				case 0:
					break;
				case 1:
					//TODO: G+ Logout  funktioniert noch nicht, loggt sich gleich wieder ein
				    if (mGoogleApiClient.isConnected()) {
				      Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
				      mGoogleApiClient.disconnect();
				      mGoogleApiClient.connect();
				    }
					break;
				//LOGOUT
				case 2:
					intent = new Intent(MainActivity.this,StatsActivity.class);
	    			startActivity(intent);
					break;
				case 3:
					intent = new Intent(MainActivity.this,LoginActivity.class);
	    			startActivity(intent);
	    			break;
				case 4:
					fragment = new SettingsFragment();
					fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment)
					.commit();
					break;
			}
		}
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
       
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	if(item.getItemId()==R.id.action_add){
    		DialogFragment fragment = new CreateTopicDialog();
    		fragment.show(getFragmentManager(),"createtopic");
    		return true;
    	}
    	/*if(item.getItemId()==R.id.action_logout){
    		Intent intent = new Intent(this,LoginActivity.class);
    		startActivity(intent);
    		return true;
    	}*/
    	/*if(item.getItemId()==R.id.action_settings){
    		Intent intent1 = new Intent(this,SettingsActivity.class);
    		startActivity(intent1);
    		System.out.println("Test");
    		return true;
    	}*/
    	if(drawerToggle.onOptionsItemSelected(item)){
    		return true;
    	}
    	return super.onOptionsItemSelected(item);
    }
    //Zurückbutton deaktivieren
    @Override
    public void onBackPressed() {
    }
    
	public void loadTopics(View view) throws InterruptedException, ExecutionException{
		Log.v("test", "Start loadTopics");
		
		try{
			TopicItem topicItem = new TopicItem(TopicRoulette.getNextTopic());
			topicItemAdapter.setRouletteItem(topicItem);
			topicItemAdapter.notifyDataSetChanged();
		}catch(IOException ex){
			Log.v("test", "IOException: " + ex.toString());
		}catch(JSONException ex){
			Log.v("test", "JSONException: " + ex.toString());
		}
	}
	
	public TopicItemAdapter getTopicItemAdapter(){
		return topicItemAdapter;
	}
	
	public void setTopicItemAdapter(TopicItemAdapter topicItemAdapter){
		this.topicItemAdapter = topicItemAdapter;
	}
    
	public void openTopic(View view){
		if(topicItemAdapter.getRouletteItem() != null){
			Fragment fragment = new TopicFragment(topicItemAdapter.getRouletteItem());
			fragmentManager.beginTransaction()
	    	.replace(R.id.content_frame,fragment)
	    	.commit();
		}
	}
	public void close(View view){
		CreateTopicDialog.alert.dismiss();
		System.out.println("CLOSE");
	}
	public void submit(View view) throws InterruptedException, ExecutionException, IOException, JSONException{
		TextView topicTitle = (TextView) findViewById(R.id.edittext_topic_title);
		String titleString = topicTitle.getText().toString();
		
		TextView topicDesc = (TextView) findViewById(R.id.edittext_topic_description);
		String descString = topicDesc.getText().toString();
		
		System.out.println("title: " + titleString + "desc: " + descString);	
		
		String result1 = CreditSystem.newTopic();
		if(!result1.equals("toLess")){
			String result = new ServiceExecuter().execute("addTopic", titleString, descString).get();
			String result2 = new ServiceExecuter().execute("addTopicCount").get();
			System.out.println("Add: " + result + "Count: " + result2);	
		}
	}
}

