package com.betteridea;

/**
 * Author: 		Better Idea
 * Description:	RegisterActivity 
 * 				Hier wird der User hingeleitet, falls zu seiner G+/FB-Email kein Account besteht.
 * 				Nach Wahl eines Nutzernamens wird der User automatisch eingelogt.
 * 
 * TODOS:		keine
 * 
 */

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import com.betteridea.connection.Login;
import com.betteridea.connection.ServiceExecuter;
import com.betteridea.logic.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	private String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        
        //Von MainActivity übergebene Mailadresse in Variable speichern
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
    //onClick register Button
  	public void register(View view){
        TextView userNameTextView = (TextView) findViewById(R.id.userName);
        String userName = userNameTextView.getText().toString();
        String result = "false";
        //Service ansprechen um User anzulegen
        try {
        	// Data1 = Username / Data2 = Usermail (Serviceparameter)
			result = new ServiceExecuter().execute("createUser",userName,email).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
        if(result=="true"){
        	Toast.makeText(this, "Register successful!", Toast.LENGTH_LONG).show();
        	View view2 = this.findViewById(android.R.id.content);
        	try {
				gointoMain(view2, email);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
        }else{
        	Toast.makeText(this, "Register unsuccessful!", Toast.LENGTH_LONG).show();
        }
    }
  	
  	//Nach Registrierung in die Main gehen
	public void gointoMain(View view, String email) throws InterruptedException, ExecutionException{
		String result = new Login().execute(email).get();
		if(result != null){
			System.out.println("Sign in succeeded.");
			try {
				TopicRoulette.loadTopicCache();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
			startActivity(intent);
		}else{
			System.out.println("Invalid email!!!");
			Toast.makeText(this, "Ungültige Mail!", Toast.LENGTH_LONG).show();
		}
	}//gointoMain()
}