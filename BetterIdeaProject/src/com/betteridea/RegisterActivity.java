package com.betteridea;

import java.util.concurrent.ExecutionException;

import com.betteridea.connection.Login;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class RegisterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	System.out.println("Register created");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        
        
        
        
        
        
        // Tests für die DB...
        
        
        
        
        
        
        
        
        Button myButton = (Button)findViewById(R.id.button1);
        Button myButton2 = (Button)findViewById(R.id.button2);
        
        final TextView myText = (TextView)findViewById(R.id.test_db_textview);
        final TextView myText1 = (TextView)findViewById(R.id.test_db_textview2);
        

                
        myButton2.setOnClickListener(new OnClickListener() {
        	public void onClick(View arg1) {
				try {
					com.betteridea.connection.Services.service.getUserData("lisa.schmidt@thomascookag.com", myText1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        });

    }
	public void gointoMain(View view) throws InterruptedException, ExecutionException{
		String result = new Login().execute("shopping4f@googlemail.com").get();
		if(result != null){
			System.out.println("Sign in succeeded.");
			Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
			startActivity(intent);
		}else{
			System.out.println("Invalid email!!!");
		}

	}
}