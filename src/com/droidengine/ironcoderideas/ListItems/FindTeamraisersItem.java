package com.droidengine.ironcoderideas.ListItems;

public class FindTeamraisersItem implements Item{

	private static final String label = "Find More Teamraisers Near You";
	
	public String getLabelText(){
		return label;
	}
	
	@Override
	public boolean isSection() {
		return true;
	}

}
