package com.betteridea;

import android.app.Activity;
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
        Button myButton = (Button)findViewById(R.id.button1);
        Button myButton2 = (Button)findViewById(R.id.button2);
        
        final TextView myText = (TextView)findViewById(R.id.test_db_textview);
        final TextView myText1 = (TextView)findViewById(R.id.test_db_textview2);
        
        myButton.setOnClickListener(new OnClickListener() {
        	public void onClick(View arg0) {
				try {
					com.betteridea.connection.Services.getUserData("s.grabaum@gmx.de", myText);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        });
                
        myButton2.setOnClickListener(new OnClickListener() {
        	public void onClick(View arg1) {
				try {
					com.betteridea.connection.Services.getUserData("lisa.schmidt@thomascookag.com", myText1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        });

    }
}