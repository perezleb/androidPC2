package com.droidengine.ironcoderideas.ListItems;


public class TeamraiserItem implements Item {
	
	private String teamraiserName;
	private String teamraiserLocation;
	private String fr_id;
	
	public void setTeamraiserName(String name){
		teamraiserName = name;
	}
	
	public void setTeamraiserLocation(String location){
		teamraiserLocation = location;
	}
	
	public void setTeamraiserID(String id){
		fr_id = id;
	}
	
	public String getTeamraiserName(){
		return teamraiserName;
	}
	
	public String getTeamraiserLocation(){
		return teamraiserLocation;
	}
	
	public String getTeamraiserID(){
		return fr_id;
	}
	
	@Override
	public boolean isSection() {
		return false;
	}
	
}
