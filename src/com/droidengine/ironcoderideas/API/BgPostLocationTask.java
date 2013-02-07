package com.droidengine.ironcoderideas.API;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import com.droidengine.ironcoderideas.ListItems.TRGPSConstants;

public class BgPostLocationTask extends AsyncTask<Void, Void, Void> {

	private Location _loc;
    private static String POST_API = "postUserLocation";
   
	private String _consId;
	private String _frId;
	private String _teamName;
	
	public BgPostLocationTask(String id, String cons_id, String name, Location location) {
		_frId = id;
		_consId = cons_id;
		_teamName = name;
		_loc = location;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		HttpClient httpclient = new DefaultHttpClient();
		try {                
			HttpPost httppost = new HttpPost(TRGPSConstants.SERVER_URL + POST_API);
        	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
            nameValuePairs.add(new BasicNameValuePair("frId", _frId));
            nameValuePairs.add(new BasicNameValuePair("consId", _consId));
            nameValuePairs.add(new BasicNameValuePair("teamName", _teamName));
            nameValuePairs.add(new BasicNameValuePair("latitude", String.valueOf(_loc.getLatitude())));
            nameValuePairs.add(new BasicNameValuePair("longitude", String.valueOf(_loc.getLongitude())));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            httpclient.execute(httppost); 
            Log.w("LOGIN", "Execute HTTP Post Request");
                        
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
         e.printStackTrace();
        } 

        httpclient.getConnectionManager().shutdown();
		return null;
	}
	
	@Override
    protected void onPostExecute(Void v) {
    	super.onPostExecute(v);
    }

}
