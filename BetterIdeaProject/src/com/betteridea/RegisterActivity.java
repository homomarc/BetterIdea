package com.betteridea;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class RegisterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen_marc);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	switch(item.getItemId()){
    	case R.id.action_add:
    		return true;
    	case R.id.action_logout:
    		Intent intent = new Intent(this,LoginActivity.class);
    		startActivity(intent);
    		System.out.println("Test");
    		return true;
    	case R.id.action_settings:
    		Intent intent1 = new Intent(this,SettingsActivity.class);
    		startActivity(intent1);
    		System.out.println("Test");
    		return true;
    	default:
    		return super.onOptionsItemSelected(item);
    	}
    }
}
