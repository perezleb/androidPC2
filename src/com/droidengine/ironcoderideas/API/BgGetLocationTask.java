package com.droidengine.ironcoderideas.API;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.util.Log;

import com.droidengine.ironcoderideas.ListItems.TRGPSConstants;
import com.droidengine.ironcoderideas.ListItems.TeammateModel;

public class BgGetLocationTask extends AsyncTask<Void, Void, List<TeammateModel>> {
	private static String GET_API = "getTeammateLocation";
	private String _consId;
	private String _frId;
	private String _teamName;
	
	public BgGetLocationTask(String id, String cons_id, String name) {
		_frId = id;
		_consId = cons_id;
		_teamName = name;
	}
	
	@Override
	protected List<TeammateModel> doInBackground(Void... params) {
		HttpClient httpclient = new DefaultHttpClient();
		List<TeammateModel> teammates = new ArrayList<TeammateModel>();
		try {                          
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
            nameValuePairs.add(new BasicNameValuePair("frId", _frId));
            nameValuePairs.add(new BasicNameValuePair("consId", _consId));
            nameValuePairs.add(new BasicNameValuePair("teamName", _teamName));
            String url = TRGPSConstants.SERVER_URL + GET_API + "?" + URLEncodedUtils.format(nameValuePairs, "utf-8");
            HttpGet httpget = new HttpGet(url);
            HttpResponse response = httpclient.execute(httpget); 
            InputStream is = response.getEntity().getContent();
            JSONObject jsonObj = new JSONObject(convertInputStreamToString(is));
            is.close();
            teammates = parseJSON(jsonObj);
            Log.w("LOGIN", "Execute HTTP Post Request");
                        
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
         e.printStackTrace();
        } catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        httpclient.getConnectionManager().shutdown();
		return teammates;
	}
	
	@Override
    protected void onPostExecute(List<TeammateModel> teammates) {
    	super.onPostExecute(teammates);
    }
	
	private String convertInputStreamToString(InputStream is) {
		String result = ""; 
		try {
	            BufferedReader reader = new BufferedReader(new InputStreamReader(
	                    is, "iso-8859-1"), 8);
	            StringBuilder sb = new StringBuilder();
	            String line = null;
	            while ((line = reader.readLine()) != null) {
	                sb.append(line + "\n");
	            }
	            result = sb.toString();
	        } catch (Exception e) {
	            Log.e("Buffer Error", "Error converting result " + e.toString());
	        }
		return result;
	}
	
	private List<TeammateModel> parseJSON(JSONObject jsonObj) throws JSONException {
		List<TeammateModel> teammates = new ArrayList<TeammateModel>();
		JSONArray jsonAry = jsonObj.getJSONArray("teammates");
        for(int i = 0; i < jsonAry.length(); i++) {
        	Location location = new Location(LocationManager.GPS_PROVIDER);
        	location.setLatitude(Double.valueOf(jsonAry.getJSONObject(i).optString("latitude")));
        	location.setLongitude(Double.valueOf(jsonAry.getJSONObject(i).optString("longitude")));
        	TeammateModel teammate = new TeammateModel(jsonAry.getJSONObject(i).optString("cons_id"), location);
        	teammates.add(teammate);
        }
		return teammates;
	}
}
