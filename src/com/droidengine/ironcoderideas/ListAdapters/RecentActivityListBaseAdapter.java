package com.droidengine.ironcoderideas.ListAdapters;

import java.util.ArrayList;

import com.droidengine.ironcoderideas.R;
import com.droidengine.ironcoderideas.ListItems.ActivityItem;
import com.droidengine.ironcoderideas.ListItems.ActivityListHeader;
import com.droidengine.ironcoderideas.ListItems.Item;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RecentActivityListBaseAdapter extends BaseAdapter{

	private static ArrayList activityList;
	private LayoutInflater l_Inflater;
	
	
	public RecentActivityListBaseAdapter(Context context, ArrayList<ActivityItem> items){
		activityList = items;
		l_Inflater = LayoutInflater.from(context);
	}
	
	public int getCount() {
		return activityList.size();
	}

	@Override
	public Object getItem(int position) {
		return activityList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		
		final Item item = (Item) activityList.get(position);
		
		if (item != null){
			
			if(item.isSection()){
				
				ActivityListHeader headerItem = (ActivityListHeader) item;
				
				v = l_Inflater.inflate(R.layout.activity_list_header, null);
				
				TextView header = (TextView)v.findViewById(R.id.donor_list_header);
				
				if (header != null){
					header.setText(headerItem.getHeader());
				}
			}
			else{
				ActivityItem activityItem = (ActivityItem)item;
				
				v = l_Inflater.inflate(R.layout.activity_list_items, null);
				
				TextView activity = (TextView)v.findViewById(R.id.activity);
				TextView activityDate = (TextView)v.findViewById(R.id.activity_date);
				
				if (activity != null){
					activity.setText(activityItem.getActivity());
				}
				
				if (activityDate != null){
					activityDate.setText(activityItem.getDate());
				}
			}
		}
		
		return v;
	}

}
