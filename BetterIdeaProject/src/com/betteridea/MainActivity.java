package com.betteridea;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

@SuppressLint("NewApi")
public class MainActivity extends Activity {
	private String[] navigation;
	private DrawerLayout drawerLayout;
	private ListView navigationList;
	private FragmentManager fragmentManager;
	private ActionBarDrawerToggle drawerToggle;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        setContentView(R.layout.drawer_layout);
        
        Fragment fragment = new MainFragment();
        fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
        	.add(R.id.content_frame,fragment)
        	.commit();
        
        navigation = getResources().getStringArray(R.array.navigation_entries);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationList = (ListView) findViewById(R.id.left_drawer);        
        navigationList.setAdapter(new ArrayAdapter<String>(this,R.layout.navigation_list_item,navigation));
//        navigationList.setItemChecked(0,true);
        navigationList.setOnItemClickListener(new NavigationItemClickListener());
        
        drawerToggle = new ActionBarDrawerToggle(this, 
        		drawerLayout, 
        		R.drawable.ic_drawer,
        		R.string.drawer_open,
        		R.string.drawer_close){
        	public void onDrawerClosed(View view){
        		super.onDrawerClosed(view);
        	}
        	
        	public void onDrawerOpened(View view){
        		super.onDrawerOpened(view);
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
			Fragment fragment = new MainFragment();
			
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment)
					.commit();
			
			navigationList.setItemChecked(position, true);
			getActionBar().setTitle(navigation[position]);
			drawerLayout.closeDrawer(navigationList);
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
    		return true;
    	}
    	if(item.getItemId()==R.id.action_logout){
    		Intent intent = new Intent(this,LoginActivity.class);
    		startActivity(intent);
    		return true;
    	}
    	if(item.getItemId()==R.id.action_settings){
    		Intent intent1 = new Intent(this,SettingsActivity.class);
    		startActivity(intent1);
    		System.out.println("Test");
    		return true;
    	}
    	if(drawerToggle.onOptionsItemSelected(item)){
    		return true;
    	}
    	return super.onOptionsItemSelected(item);
    }
}

