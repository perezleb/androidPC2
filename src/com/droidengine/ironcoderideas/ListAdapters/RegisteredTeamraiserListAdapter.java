package com.droidengine.ironcoderideas.ListAdapters;

import java.util.ArrayList;

import com.droidengine.ironcoderideas.R;
import com.droidengine.ironcoderideas.ListItems.ActivityItem;
import com.droidengine.ironcoderideas.ListItems.FindTeamraisersItem;
import com.droidengine.ironcoderideas.ListItems.Item;
import com.droidengine.ironcoderideas.ListItems.TeamraiserItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RegisteredTeamraiserListAdapter extends BaseAdapter {
	
	private static ArrayList eventList;
	private LayoutInflater l_Inflater;
	
	public RegisteredTeamraiserListAdapter(Context context, ArrayList<ActivityItem> events){
		eventList = events;
		l_Inflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return eventList.size();
	}

	@Override
	public Object getItem(int position) {
		return eventList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		
		final Item item = (Item) eventList.get(position);
		
		if (item != null){
			if (item.isSection()){
				FindTeamraisersItem findEventItem = (FindTeamraisersItem) item;
				
				v = l_Inflater.inflate(R.layout.find_teamraiser_item, null);
				
				TextView label = (TextView)v.findViewById(R.id.find_teamraisers_label);
				
				if (label != null){
					label.setText(findEventItem.getLabelText());
				}
				
				
			} else {
				
				TeamraiserItem event = (TeamraiserItem) item;
				
				v = l_Inflater.inflate(R.layout.teamraiser_item, null);
				
				TextView eventName = (TextView)v.findViewById(R.id.teamraiser_name);
				TextView eventAddress = (TextView)v.findViewById(R.id.event_address);
				
				if (eventName != null){
					eventName.setText(event.getTeamraiserName());
				}
				
				if(eventAddress != null){
					eventAddress.setText(event.getTeamraiserLocation());
				}
			}
				
			
		}
		
		return v;
	}

}
