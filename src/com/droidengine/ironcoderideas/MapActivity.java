package com.droidengine.ironcoderideas;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.droidengine.ironcoderideas.API.BgGetLocationTask;
import com.droidengine.ironcoderideas.API.BgPostLocationTask;
import com.droidengine.ironcoderideas.ListItems.TRGPSConstants;
import com.droidengine.ironcoderideas.ListItems.TeammateModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity implements LocationListener {
	 
    private GoogleMap _googleMap;
    private String _frId;
    private String _teamName;
    private String _cons_id;
    private String _userName;

 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        
        Intent intent = getIntent();
        _frId = intent.getStringExtra(TRGPSConstants.ID);
        _teamName = intent.getStringExtra(TRGPSConstants.TEAM_NAME);
        _cons_id = intent.getStringExtra(TRGPSConstants.CONS_ID);
        _userName = intent.getStringExtra(TRGPSConstants.USER_NAME);
        
        // Getting reference to the SupportMapFragment of activity_map.xml
        SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
 
        // Getting GoogleMap object from the fragment
        _googleMap = fm.getMap();
 
        // Enabling MyLocation Layer of Google Map
        _googleMap.setMyLocationEnabled(true);

        // Getting LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
 
        // Creating a criteria object to retrieve provider
//        Criteria criteria = new Criteria();
//        criteria.setAccuracy(Criteria.ACCURACY_FINE);
//    	criteria.setAltitudeRequired(false);
//    	criteria.setBearingRequired(true);
//    	criteria.setCostAllowed(true);
//    	criteria.setPowerRequirement(Criteria.POWER_LOW);
        // Getting the name of the best provider
        String provider = LocationManager.GPS_PROVIDER;
 
        // Getting Current Location
        Location location = locationManager.getLastKnownLocation(provider);
 
        if(location!=null){
            onLocationChanged(location);
        }
 
        locationManager.requestLocationUpdates(provider, 5000, 0, this);
        callAsyncGetTask();
    }
 
    @Override
    public void onLocationChanged(Location location) {
//    	Log.v("LOCATION","========================I'M GETTING CALLED===================");
        TextView teamLocation = (TextView) findViewById(R.id.team_location);
 
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
 
        // Creating a LatLng object for the current location
        LatLng latLng = new LatLng(latitude, longitude);

        // Showing the current location in Google Map
        _googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        _googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
 
        // Setting latitude and longitude in the TextView tv_location
        teamLocation.setText("Latitude:" +  latitude  + ", Longitude:"+ longitude );
        callAsyncPostTask(location);
    }
 
    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }
 
    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }
 
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    	if(status == LocationProvider.AVAILABLE) {
    		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    		Location location = locationManager.getLastKnownLocation(provider);
    		if(location!=null) {
                onLocationChanged(location);
            }
    	}
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_map, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void callAsyncPostTask(final Location loc) {
//		final Handler handler = new Handler();
//		Timer timer = new Timer();
//		TimerTask doAsyncTask = new TimerTask() {
//			@Override
//			public void run() {
//				handler.post(new Runnable() {
//					public void run() {
						BgPostLocationTask bgTask = new BgPostLocationTask(_frId, _userName, _cons_id, _teamName, loc);
						bgTask.execute();
//					}
//				});
//				
//			}
//		};
//		timer.schedule(doAsyncTask, 0, 5000);
	}
	
	public void callAsyncGetTask() {
		final Handler handler = new Handler();
		Timer timer = new Timer();
		TimerTask doAsyncTask = new TimerTask() {
			@Override
			public void run() {
				handler.post(new Runnable() {
					public void run() {
						BgGetLocationTask bgTask = new BgGetLocationTask(_frId, _userName, _cons_id, _teamName);
						final AsyncTask<Void, Void, List<TeammateModel>> taskResult = bgTask.execute();
						List<TeammateModel> tmList;
						try {
							tmList = taskResult.get();
							_googleMap.clear();
							for(TeammateModel teammate : tmList) {
								LatLng latLng = new LatLng(teammate.getLatitude(), teammate.getLongitude());
								MarkerOptions marker = new MarkerOptions();
								marker.position(latLng);
								marker.title(teammate.getUserName());
								_googleMap.addMarker(marker);
							}
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ExecutionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				});
				
			}
		};
		timer.schedule(doAsyncTask, 0, 5000);
	}

}

