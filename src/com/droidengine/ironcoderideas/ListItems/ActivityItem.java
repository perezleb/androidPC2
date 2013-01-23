package com.droidengine.ironcoderideas.ListItems;


public class ActivityItem implements Item {
	
	private String firstName;
	private String lastName;
	private String amountDonated;

	public void setDonor(String first, String last, String amount){
		firstName = first;
		lastName = last;
		amountDonated = amount;
	}
	
	public String getFirstName(){
		return firstName;
	}
	
	public String getLastName(){
		return lastName;
	}
	
	public String getAmountDonated(){
		return amountDonated;
	}
	
	@Override
	public boolean isSection() {
		return false;
	}
	
}
