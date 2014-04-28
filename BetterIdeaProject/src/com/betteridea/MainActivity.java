package com.betteridea;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.betteridea.adapter.IdeaItemAdapter;
import com.betteridea.adapter.NavDrawerListAdapter;
import com.betteridea.adapter.TopicItemAdapter;
import com.betteridea.connection.Service;
import com.betteridea.connection.ServiceExecuter;
import com.betteridea.fragments.CreateTopicFragment;
import com.betteridea.fragments.HomeFragment;
import com.betteridea.fragments.SettingsFragment;
import com.betteridea.fragments.TopicCloseFragment;
import com.betteridea.fragments.TopicFragment;
import com.betteridea.fragments.TopicOwnFragment;
import com.betteridea.logic.CreditSystem;
import com.betteridea.logic.TopicRoulette;
import com.betteridea.models.IdeaItem;
import com.betteridea.models.NavDrawerItem;
import com.betteridea.models.TopicItem;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;

@SuppressLint("NewApi")
public class MainActivity extends Activity {
	private DrawerLayout drawerLayout;
	private ListView navigationList;
	private static FragmentManager fragmentManager;
	private ActionBarDrawerToggle drawerToggle;
	
//	NavigationDrawer Items (Icons, Entries)
	private String[] navigationEntries;
	private TypedArray navigationIcons;
	
//	NavigationItem
	private ArrayList<NavDrawerItem> navigationItems;
	
//	Navigation Adapter
	private NavDrawerListAdapter adapter;
	private TopicItemAdapter topicItemAdapter;
	private IdeaItemAdapter ideaItemAdapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);
        
//        notify("Neue Ideen zu deinen Topics vorhanden!");
        fragmentManager = getFragmentManager();
        Fragment homeFragment = fragmentManager.findFragmentByTag("HomeFragment");
        if(homeFragment == null)
        	homeFragment = new HomeFragment();
        
        fragmentManager.beginTransaction()
        	.replace(R.id.content_frame,homeFragment,"HomeFragment")
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
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
		
		private void selectItem(int position){
			Fragment fragment = null;
			
			navigationList.setItemChecked(position, true);
			getActionBar().setTitle(navigationEntries[position]);
			drawerLayout.closeDrawer(navigationList);
			Intent intent = new Intent();
			switch(position){
				case 0:
					fragment = (Fragment)getFragmentManager().findFragmentByTag("HomeFragment");
			        if(fragment == null){
			        	fragment = new HomeFragment();
			        }
			        fragmentManager = getFragmentManager();
			        fragmentManager.beginTransaction()
			        	.replace(R.id.content_frame,fragment,"HomeFragment")
			        	.commit();
					break;
				case 1:
					//TODO: 
					fragment = new TopicOwnFragment();
					fragmentManager.beginTransaction()
			    	.replace(R.id.content_frame,fragment)
			    	.commit();
					break;
				//LOGOUT
				case 2:
					intent = new Intent(MainActivity.this,StatsActivity.class);
	    			startActivity(intent);
					break;
				case 3:
					fragment = new SettingsFragment();
					fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment)
					.commit();
					break;
				case 4:
					intent = new Intent(MainActivity.this,LoginActivity.class);
	    			startActivity(intent);
	    			break;
			}
		}
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present       
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	if(item.getItemId()==R.id.action_add){
    		CreateTopicFragment fragment = new CreateTopicFragment();
    		fragmentManager.beginTransaction()
	    	.replace(R.id.content_frame,fragment)
	    	.commit();
    		return true;
    	}
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
			TopicItem topicItem = new TopicItem(TopicRoulette.getNextTopic(),true);
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
	
	public IdeaItemAdapter getIdeaItemAdapter(){
		return ideaItemAdapter;
	}
	
	public void setIdeaItemAdapter(IdeaItemAdapter ideaItemAdapter){
		this.ideaItemAdapter = ideaItemAdapter;
	}
	
	public void share(View view){
		String text = "Marc braucht Hilfe beim proggen ihr Schweine...";
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT,  ""+ text +"\n---------------\n---sent via----\nBetter Idea app, available at: https://play.google.com/store/apps/details?id=com.betteridea");
		sendIntent.setType("text/plain");
		startActivity(Intent.createChooser(sendIntent, "Share text with..."));
	}
	
	public void close(View view){
//		CreateTopicDialog.alert.dismiss();
//		System.out.println("CLOSE");
	}
	
	/*
	 * Create Topic:
	 */
	public void submit(View view) throws InterruptedException, ExecutionException, IOException, JSONException{
		TextView topicTitle = (TextView) findViewById(R.id.edittext_topic_title);
		String titleString = topicTitle.getText().toString();
		
		TextView topicDesc = (TextView) findViewById(R.id.edittext_topic_description);
		String descString = topicDesc.getText().toString();
		
//		System.out.println("title: " + titleString + "desc: " + descString);	
		
		String result1 = CreditSystem.newTopic();
		if(!result1.equals("toLess")){
			String result = new ServiceExecuter().execute("addTopic", titleString, descString).get();
			if(result.equals("true")){
				String result2 = new ServiceExecuter().execute("setScore").get();
				if(result2.equals("true")){
					Fragment fragment = new HomeFragment();
					fragmentManager = getFragmentManager();fragmentManager.beginTransaction().add(R.id.content_frame,fragment).commit();
					Toast.makeText(this, "Thema angelegt.", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(this, "Übertragung fehlgeschlagen.", Toast.LENGTH_SHORT).show();
				}
			}else{
				Toast.makeText(this, "Übertragung fehlgeschlagen.", Toast.LENGTH_SHORT).show();
			}
		}else{
			Toast.makeText(this, "Zu wenig Credits.", Toast.LENGTH_SHORT).show();
		}
	}
	
	/*
	 * Idee einreichen
	 */
	public void sendIdea(View view) throws InterruptedException, ExecutionException, JSONException{
		EditText ideaText = (EditText) findViewById(R.id.text_enter_idea);
		String text = ideaText.getText().toString();
		Log.v("MainActivity (SendIdea)", "Idee: " + ideaText.getText().toString());
		java.util.Date now = new java.util.Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.GERMANY);
		String date = sdf.format(now);
		IdeaItem idea = new IdeaItem(ideaText.getText().toString(),date,false,"",false,-1,-1,Service.userData.getString("userName"));
		ideaItemAdapter.addIdea(idea);
		ideaItemAdapter.notifyDataSetChanged();
		ideaText.setText("");
		//An Server senden
		String result = null;
		try {
			result = new ServiceExecuter().execute("addIdea", text, topicItemAdapter.getRouletteItem().getTopicID()+"").get();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(result.equals("true")){
			String result1 = new ServiceExecuter().execute("setScore").get();
			if(result1.equals("true")){
				Toast.makeText(this, "Idee eingereicht", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(this, "Übertragung fehlgeschlagen.", Toast.LENGTH_SHORT).show();
			}
		}else{
			Toast.makeText(this, "Übertragung fehlgeschlagen.", Toast.LENGTH_SHORT).show();
		}
	}
	public void makeToast(String value){
		Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
	}
	
	private void notify(String text) {
	    String name = "Better Idea";
	    String[] strings = name.split("\\.");
	    Notification noti = new Notification.Builder(this)
	        .setContentTitle(name).setAutoCancel(true)
	        .setSmallIcon(R.drawable.ic_launcher)
	        .setContentText(text).build();
	    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	    notificationManager.notify((int) System.currentTimeMillis(), noti);
	  }
}