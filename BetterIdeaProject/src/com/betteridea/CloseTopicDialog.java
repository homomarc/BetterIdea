package com.betteridea;

/**
 * Author: 		Better Idea
 * Description:	CloseTopicDialog 
 * 				Dialogbox zum schlie�en eines Themas
 * 
 * 
 * TODOS:		keine
 * 
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;

public class CloseTopicDialog extends DialogFragment {
	public static AlertDialog alert = null;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState){
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		
		builder.setView(inflater.inflate(R.layout.create_topic_layout, null));
		
		builder.setTitle(R.string.label_create_topic_headline);
		alert = builder.create();
		return alert;
	}
}