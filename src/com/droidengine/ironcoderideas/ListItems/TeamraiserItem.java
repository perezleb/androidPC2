package com.droidengine.ironcoderideas.ListItems;

import java.text.DateFormat;
import java.util.Date;



public class TeamraiserItem implements Item {
	
	private String teamraiserName;
	private String teamName;
	private String teamraiserLocation;
	private String fr_id;
	private String streetAddress;
	private String city;
	private String state;
	private String zip;
	private String country;
	private String url;
	private Date date;
	
	public void setTeamraiserName(String name){
		teamraiserName = name;
	}
	
	public void setTeamName(String name) {
		teamName = name;
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
	
	public String getTeamName() {
		return teamName;
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

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getFullAddress(){
		StringBuilder sb = new StringBuilder();
		
		if(streetAddress != null)
			sb.append(streetAddress);	
		sb.append("\n");
		if(city != null)
			sb.append(city + ", ");	
		if(state != null)
			sb.append(state + ", ");	
		if(zip != null)
			sb.append(zip);	
		
		return sb.toString();
	}

	public String getDate() {
		DateFormat longDf = DateFormat.getDateInstance(DateFormat.LONG);
		return longDf.format(date);
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url){
		this.url = url;
	}
}
