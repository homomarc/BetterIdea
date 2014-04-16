package com.betteridea;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.betteridea.connection.Login;
import com.betteridea.connection.Service;
import com.betteridea.logic.TopicRoulette;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener,
ConnectionCallbacks, OnConnectionFailedListener {
	
	  //Instanz
	public static LoginActivity loginactivity = new LoginActivity();

	  //Logcat Tag
	  private static final String TAG = "LoginActivity";
	  
	  private static final int RC_SIGN_IN = 0;

	  // GoogleApiClient 
	  private GoogleApiClient mGoogleApiClient;
	  
	  //Flag welches ausschließt, damit nächster Intent nicht
	  //geöffnet wird ohne dass Vorgang abgeschlossen ist
	  private boolean mIntentInProgress;
	  
	  private boolean mSignInClicked;
	  
	  private ConnectionResult mConnectionResult;
	  
	  private SignInButton mSignInButton;

	private String personName;

	private String email;
	  
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.login_activity);

	    mSignInButton = (SignInButton) findViewById(R.id.sign_in_button);

	    mSignInButton.setOnClickListener(this);
	    
	    mGoogleApiClient = buildGoogleApiClient();
	  }
	  
	  private GoogleApiClient buildGoogleApiClient() {
	    // When we build the GoogleApiClient we specify where connected and
	    // connection failed callbacks should be returned, which Google APIs our
	    // app uses and which OAuth 2.0 scopes our app requests.
	    return new GoogleApiClient.Builder(this)
	        .addConnectionCallbacks(this)
	        .addOnConnectionFailedListener(this)
	        .addApi(Plus.API, null)
	        .addScope(Plus.SCOPE_PLUS_LOGIN)
	        .build();
	  }

	  @Override
	  protected void onStart() {
	    super.onStart();
	    mGoogleApiClient.connect();
	  }

	  @Override
	  protected void onStop() {
	    super.onStop();
	    if (mGoogleApiClient.isConnected()) {
	      mGoogleApiClient.disconnect();
	    }
	  }
	  
	  /**
	  * Method to resolve any signin errors
	  * */
	  private void resolveSignInError() {
	    if (mConnectionResult.hasResolution()) {
	        try {
	            mIntentInProgress = true;
	            mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
	        } catch (SendIntentException e) {
	            mIntentInProgress = false;
	            mGoogleApiClient.connect();
	        }
	    }
	  }
	  
	  @Override
	    public void onConnectionFailed(ConnectionResult result) {
	        if (!result.hasResolution()) {
	            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
	                    0).show();
	            return;
	        }
	 
	        if (!mIntentInProgress) {
	            // Store the ConnectionResult for later usage
	            mConnectionResult = result;
	 
	            if (mSignInClicked) {
	                // The user has already clicked 'sign-in' so we attempt to
	                // resolve all
	                // errors until the user is signed in, or they cancel.
	                resolveSignInError();
	            }
	        }
	 
	    }
	  @Override
	    protected void onActivityResult(int requestCode, int responseCode,
	            Intent intent) {
	        if (requestCode == RC_SIGN_IN) {
	            if (responseCode != RESULT_OK) {
	                mSignInClicked = false;
	            }
	 
	            mIntentInProgress = false;
	 
	            if (!mGoogleApiClient.isConnecting()) {
	                mGoogleApiClient.connect();
	            }
	        }
	    }
	  @Override
	    public void onConnected(Bundle arg0) {
	        mSignInClicked = false;
	        Toast.makeText(this, "Login successful!", Toast.LENGTH_LONG).show();
	 
	        // Get user's information
	        if(getProfileInformation()==true){
	        	// Update the UI after signin
		        try {
					updateUI(true);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
	        }
	    }
	 
	    /**
	     * Updating the UI, showing/hiding buttons and profile layout
	     * @throws InterruptedException 
	     * @throws ExecutionException 
	     * */
	    private void updateUI(boolean isSignedIn) throws InterruptedException, ExecutionException {
	        if (isSignedIn) {
	    		String result = new Login().execute(email).get();
	    		if(result != null){
	    			System.out.println("Sign in succeeded.");
	    			Intent intent = new Intent(LoginActivity.this,MainActivity.class);
	    			startActivity(intent);
	    		}else{
	    			System.out.println("Invalid email!");
	    			Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
	    			startActivity(intent);
	    		}
	        } else {
	        	//TODO: Do nothing?
	        	
	        }
	    }
	 
	    /**
	     * Fetching user's information name, email, profile pic
	     * */
	    private boolean getProfileInformation() {
	        try {
	            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
	                Person currentPerson = Plus.PeopleApi
	                        .getCurrentPerson(mGoogleApiClient);
	                personName = currentPerson.getDisplayName();
	                String personPhotoUrl = currentPerson.getImage().getUrl();
	                String personGooglePlusProfile = currentPerson.getUrl();
	                email = Plus.AccountApi.getAccountName(mGoogleApiClient);
	 
	                Log.e(TAG, "Name: " + personName + ", plusProfile: "
	                        + personGooglePlusProfile + ", email: " + email
	                        + ", Image: " + personPhotoUrl);
	 	 
	            } else {
	                Toast.makeText(getApplicationContext(),
	                        "Person information is null", Toast.LENGTH_LONG).show();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return true;
	    }
	 
	    @Override
	    public void onConnectionSuspended(int arg0) {
	        mGoogleApiClient.connect();
	        try {
				updateUI(false);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
	    }
	    
//	  @Override
//	  protected void onSaveInstanceState(Bundle outState) {
//	    super.onSaveInstanceState(outState);
//	    outState.putInt(SAVED_PROGRESS, mSignInProgress);
//	  }

	  @Override
	  public void onClick(View v) {
	   
	      switch (v.getId()) {
	          case R.id.sign_in_button:
	        	  if (!mGoogleApiClient.isConnecting()) {
	                  mSignInClicked = true;
	                  resolveSignInError();
	              }
	            break;
	    }
	  }
	  
	  /**
	   * Sign-out from google wird momentan nicht verwendet
	 * @throws InterruptedException 
	 * @throws ExecutionException 
	   * */
	  public void signOutFromGplus() throws InterruptedException, ExecutionException {
	      if (mGoogleApiClient.isConnected()) {
	          Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
	          mGoogleApiClient.disconnect();
	          mGoogleApiClient.connect();
	          updateUI(false);
	      }
	  }
	  
  	//onClick Login Button
	public void login(View view){
		Intent intent;
		//		Check Password
		/*EditText user = (EditText) findViewById(R.id.user_edit);
		EditText password = (EditText) findViewById(R.id.password_edit);*/
		
		try{
			String result = new Login().execute("rene.kirchhoff90@googlemail.com").get();
			if(result != null){
				String check = TopicRoulette.loadTopicCache();
				if(check != "false"){
					System.out.println("Sign in succeeded.");
					intent = new Intent(LoginActivity.this,MainActivity.class);
		//			startActivity(intent);
				}
			}else{
				System.out.println("Invalid email!");
				intent = new Intent(LoginActivity.this,RegisterActivity.class);
	//			startActivity(intent);
			}
		
			/*if(checkPassword(user.getText().toString(),password.getText().toString()))*/
				startActivity(intent);
			/*else
				cancelLogin();*/
		}catch(Exception ex){
			Log.v("test",ex.toString());
		}
	}
	
	private boolean checkPassword(String user, String password){
		return true;
	}
	private void cancelLogin(){
		
	}
	//	onClick Register Button
	/*
	public void register(View view){
		Intent intent = new Intent(this,RegisterActivity.class);
		startActivity(intent);
	}*/
  

}
