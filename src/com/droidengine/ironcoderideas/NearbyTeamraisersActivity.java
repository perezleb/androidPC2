package com.droidengine.ironcoderideas;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class NearbyTeamraisersActivity extends android.support.v4.app.FragmentActivity  {
	private static final String TAG = "IRONCODER";
	
	private GoogleMap  mMap;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nearby_teamraisers);
		setUpMapIfNeeded();

		
	}
	
	
	private void setUpMapIfNeeded() {
	    // Do a null check to confirm that we have not already instantiated the map.
		if(mMap == null){
	        mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
	        // Check if we were successful in obtaining the map.
	        if (mMap != null) {
	        	
	        	mMap.setMyLocationEnabled(true);
	        }
	    }
	}

}
