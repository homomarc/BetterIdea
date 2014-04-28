package com.betteridea.fragments;

/**
 * Author: 		Better Idea
 * Description:	SettingsFragment enth‰lt aktuell ausschlieﬂlich die Funktion
 * 				den Username des eingeloggten Users zu ‰nden.
 * 
 * TODOS:		keine
 * 
 */

import java.util.concurrent.ExecutionException;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.betteridea.R;
import com.betteridea.connection.ServiceExecuter;

public class SettingsFragment extends Fragment{
	
	// Konstruktor zur Orientation-Drehung notwendig
	public SettingsFragment(){}
	
	@Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.v("Load saved Instance test","SettingsFragment");
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState){
		final View view = inflater.inflate(R.layout.settings_fragment, container, false);
		
		Button button = (Button) view.findViewById(R.id.button1);
			button.setOnClickListener(new OnClickListener()
			   {
	             public void onClick(View v)
	             {
	            	EditText userNameT = (EditText) view.findViewById(R.id.editTextUserName);
	         		String newUserName = userNameT.getText().toString();
	         		
	         		String result;
					try {
						result = new ServiceExecuter().execute("changeName", newUserName).get();
		         		System.out.println("Username change request: "+result);
		         		if(result == "true"){
		         			Toast.makeText(getActivity(), "Nutzername erfolgreich ge‰ndert", Toast.LENGTH_LONG).show();
		         		}
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (ExecutionException e) {
						e.printStackTrace();
					}
	         		
	             } 
			   }); 
		return view;
	}
}