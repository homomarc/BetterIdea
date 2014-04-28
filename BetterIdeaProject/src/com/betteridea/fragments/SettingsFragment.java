package com.betteridea.fragments;

import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.betteridea.R;
import com.betteridea.connection.ServiceExecuter;

public class SettingsFragment extends Fragment{
	
	public SettingsFragment(){
		
	}
	
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
				         			Toast.makeText(getActivity(), "Nutzername erfolgreich geändert", Toast.LENGTH_LONG).show();
				         		}
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (ExecutionException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			         		
			             } 
			   }); 
		
		return view;
	}
}
