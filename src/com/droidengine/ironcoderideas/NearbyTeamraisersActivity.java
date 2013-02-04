package com.droidengine.ironcoderideas;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.droidengine.ironcoderideas.API.NearbyTeamraisersAPI;
import com.droidengine.ironcoderideas.ListItems.TeamraiserItem;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class NearbyTeamraisersActivity extends android.support.v4.app.FragmentActivity implements InfoWindowAdapter, OnInfoWindowClickListener {
	private static final String TAG = "IRONCODER";

	private GoogleMap mMap;
	private Geocoder geocoder;
	private Location currentLocation;
	
	private List<TeamraiserItem> teamraisers;
	private HashMap<Marker, TeamraiserItem> eventMarkerMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		geocoder = new Geocoder(this);
		eventMarkerMap = new HashMap<Marker, TeamraiserItem>();
				
		setContentView(R.layout.nearby_teamraisers);
		setUpMapIfNeeded();

		final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		LocationListener locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				currentLocation = location;
				processLocation();
				locationManager.removeUpdates(this);
			}

			public void onStatusChanged(String provider, int status, Bundle extras) { }

			public void onProviderEnabled(String provider) { }

			public void onProviderDisabled(String provider) { }
		};

		locationManager.requestLocationUpdates( LocationManager.NETWORK_PROVIDER, 0, 1609 * 2, locationListener);

	}
	
	
	private void processLocation(){
		try {
			List<Address> addressees = geocoder.getFromLocation(
					currentLocation.getLatitude(),
					currentLocation.getLongitude(), 1);

			String postalCode = addressees.get(0).getPostalCode();
			NearbyTeamraisersAPI manager = new NearbyTeamraisersAPI(postalCode);
			try {
				teamraisers = manager.getNearbyTeamraisers();
				for(TeamraiserItem item : teamraisers){
					addToMap(item);
				}
			} catch (Exception e) {
				Log.d(TAG, e.getMessage());
			}

		} catch (IOException e) {
			Log.d(TAG, "Unable to obtain current location");
		}

	}
	
	
	private void addToMap(TeamraiserItem item){
		Log.d(TAG, item.getFullAddress());
		
		List<Address> addressees;
		try {
			String address = item.getFullAddress();
			addressees = geocoder.getFromLocationName(
					address, 1);
			if(addressees.size() > 0) {
				Address match = addressees.get(0);
				eventMarkerMap.put(
				mMap.addMarker(new MarkerOptions()
		        .position(new LatLng(match.getLatitude(), match.getLongitude()))
		        .title(item.getTeamraiserName())), item);
			} else {
				Log.d(TAG, "Unable to reverse geolocate address");
			}
		} catch (IOException e) {
			Log.d(TAG, e.getMessage());
		}
		


	}

	private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the
		// map.
		if (mMap == null) {
			mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
			// Check if we were successful in obtaining the map.
			if (mMap != null) {
				mMap.setOnInfoWindowClickListener(this);
				mMap.setInfoWindowAdapter(this);
				mMap.setMyLocationEnabled(true);
			}
		}
		
		
	}


	@Override
	public View getInfoContents(Marker marker) {
		  // RelativeLayout layout = ((RelativeLayout)findViewById(R.id.teamraiserMapItem));
		   
		   View layout = getLayoutInflater().inflate(R.layout.teamraiser_map_item, null);
		   
		   TextView eventName = ((TextView)layout.findViewById(R.id.eventName));
		   TextView eventDate = ((TextView)layout.findViewById(R.id.eventDate));
		   TextView eventAddress = ((TextView)layout.findViewById(R.id.eventAddress));

		   TeamraiserItem item = eventMarkerMap.get(marker);
		   if(item != null){
			   eventName.setText(item.getTeamraiserName());     
			   eventDate.setText(item.getDate());
			   eventAddress.setText(item.getFullAddress());
		   }
		      
		   
		   return layout;

	}


	@Override
	public View getInfoWindow(Marker marker) {
		return null;
	}


	@Override
	public void onInfoWindowClick(Marker marker) {
		 TeamraiserItem item = eventMarkerMap.get(marker);
		   if(item != null){
			   Intent register = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getUrl()));
			   startActivity(register);
		   }
	}



}
