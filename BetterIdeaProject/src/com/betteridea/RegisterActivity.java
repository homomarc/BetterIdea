package com.betteridea;

import java.util.concurrent.ExecutionException;
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
        myButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
				String str = null;
				String result = null;
				try {
					result = new DatabaseTest().execute(str).get();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
    	        final TextView myText = (TextView)findViewById(R.id.test_db_textview);
    	        
				myText.setText(result);
            }
        });
    }
}