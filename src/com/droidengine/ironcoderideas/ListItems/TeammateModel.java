package com.droidengine.ironcoderideas.ListItems;

import android.location.Location;

public class TeammateModel {

	private String _consId;
	private Location _location;
	
	public TeammateModel(String consId, Location location) {
		_consId = consId;
		_location = location;
	}
	
	public String getConsId() {
		return _consId;
	}
	
	public Location getLocation() {
		return _location;
	}
	
	public double getLatitude() {
		return _location.getLatitude();
	}
	
	public double getLongitude() {
		return _location.getLongitude();
	}
}
