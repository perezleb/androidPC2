package com.droidengine.ironcoderideas.ListItems;


public class ActivityItem implements Item {
	
	private String activity;
	private String date;

	public void setActivity(String activity){
		this.activity = activity.replaceAll("\n\n", " ").replaceAll("  ", " ");
	}
	
	public void setDate(String date){
		this.date = date;
	}
	
	public String getActivity(){
		return this.activity;
	}
	
	public String getDate(){
		return this.date;
	}
	
	@Override
	public boolean isSection() {
		return false;
	}
	
}
