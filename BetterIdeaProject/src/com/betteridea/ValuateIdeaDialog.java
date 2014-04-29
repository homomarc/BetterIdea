package com.betteridea;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ValuateIdeaDialog extends DialogFragment {
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState){
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.valuate_idea_dialog, null);
		
		Button goodIdea = (Button)view.findViewById(R.id.button_idea_good);
		Button validIdea = (Button)view.findViewById(R.id.button_idea_valid);
		Button reportSpam = (Button)view.findViewById(R.id.button_idea_spam);
		
		goodIdea.setOnClickListener(new OnClickListener(){
			public void onClick(View view){
				if(dialogResult != null){
					dialogResult.finish("good");
				}
				ValuateIdeaDialog.this.dismiss();
			}
		});
		
		validIdea.setOnClickListener(new OnClickListener(){
			public void onClick(View view){
				if(dialogResult != null){
					dialogResult.finish("valid");
				}
				ValuateIdeaDialog.this.dismiss();
			}
		});
		
		reportSpam.setOnClickListener(new OnClickListener(){
			public void onClick(View view){
				if(dialogResult != null){
					dialogResult.finish("spam");
				}
				ValuateIdeaDialog.this.dismiss();
			}
		});
		
		builder.setView(view);
		
		builder.setTitle(R.string.label_valuate_idea_headline);
		
		return builder.create();
	}
	
	private DialogResult dialogResult;
	
	public interface DialogResult{
		void finish(String result);
	}
	
	public void setDialogResult(DialogResult result){
		this.dialogResult = result;
	}
}