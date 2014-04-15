package com.betteridea.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.betteridea.R;
import com.betteridea.models.IdeaItem;

public class IdeaItemAdapter extends BaseAdapter{
	private ArrayList<IdeaItem> ideaItems = new ArrayList<IdeaItem>();
	private Context context;
	
	public IdeaItemAdapter(Context context, ArrayList<IdeaItem> ideaItems){
		this.ideaItems = ideaItems;
		this.context = context;
	}
	
	
	@Override
	public int getCount() {
		return ideaItems.size();
	}

	@Override
	public Object getItem(int position) {
		return ideaItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup group) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.idea_item_layout, null);
		
		TextView userText = (TextView) view.findViewById(R.id.text_idea_user);
		TextView text = (TextView) view.findViewById(R.id.text_idea);
		
		text.setText(ideaItems.get(position).getText());
		userText.setText(ideaItems.get(position).getUser());
		
		return view;
	}

}
