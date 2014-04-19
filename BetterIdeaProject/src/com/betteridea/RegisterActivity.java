package com.betteridea;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import com.betteridea.connection.Login;
import com.betteridea.connection.Service;
import com.betteridea.connection.ServiceExecuter;
import com.betteridea.logic.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        
        
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                email= null;
            } else {
                email= extras.getString("EMAIL");
            }
        } else {
            email= (String) savedInstanceState.getSerializable("EMAIL");
        }
   } 
  //onClick Login Button
  	public void register(View view){
        TextView userNameTextView = (TextView) findViewById(R.id.userName);
        String userName = userNameTextView.getText().toString();
        String result = "false";
        //Service ansprechen um User anzulegen
        try {
        	// Data1 = Username / Data2 = Usermail
			result = new ServiceExecuter().execute("createUser",userName,email).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if(result=="true"){
        	Toast.makeText(this, "Register successful!", Toast.LENGTH_LONG).show();
        	View view2 = this.findViewById(android.R.id.content);
        	try {
				gointoMain(view2, email);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }else{
        	Toast.makeText(this, "Register unsuccessful!", Toast.LENGTH_LONG).show();
        }
    }
	public void gointoMain(View view, String email) throws InterruptedException, ExecutionException{
		String result = new Login().execute(email).get();
		if(result != null){
			System.out.println("Sign in succeeded.");
			try {
				TopicRoulette.loadTopicCache();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
			startActivity(intent);
		}else{
			System.out.println("Invalid email!!!");
		}

	}
}