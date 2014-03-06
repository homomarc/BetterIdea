package com.betteridea;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends Activity {
	public final static String EXTRA_USER = "com.example.betteridea.user";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
	}

//	onClick Login Button
	public void login(View view){
		Intent intent = new Intent(this,MainActivity.class);
		
//		Check Password
		EditText user = (EditText) findViewById(R.id.user_edit);
		EditText password = (EditText) findViewById(R.id.password_edit);
		
		if(checkPassword(user.getText().toString(),password.getText().toString()))
			startActivity(intent);
		else
			cancelLogin();
	}
	
	private boolean checkPassword(String user, String password){
		return true;
	}
	
	private void cancelLogin(){
		
	}
	
//	onClick Register Button
	public void register(View view){
		Intent intent = new Intent(this,RegisterActivity.class);
		startActivity(intent);
	}
}
