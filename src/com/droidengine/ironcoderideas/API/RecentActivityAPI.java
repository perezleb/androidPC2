package com.droidengine.ironcoderideas.API;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.os.AsyncTask;

import com.droidengine.ironcoderideas.ListItems.ActivityItem;

public class RecentActivityAPI extends APIResponseManager {
	private static String TAG = "IRONCODER";
	private static String METHOD = "getRecentActivity";
	private static String API = "CRTeamraiserAPI?";
	
	public String token;
	public String fr_id;
	
	
	public RecentActivityAPI(String auth, String id){
		token = auth;
		fr_id = id;
	}
	
	public ArrayList<ActivityItem> getRecentActivity() throws Exception{
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("sso_auth_token", token);
		params.put("fr_id", fr_id);
		
		APIController apiUser = new APIController(API, METHOD, params);
		AsyncTask<Void, Void, Document>  task = apiUser.execute();
		
		doc = task.get();
		//check for errorResponse
		if (isErrorMessage()){
			if (errorMessage != null)
				throw new Exception(errorMessage);
			else
				throw new Exception("Technical Difficulties");
		}
		
		return parseRecentActivity();
	}
	
	private ArrayList<ActivityItem> parseRecentActivity(){
		ArrayList<ActivityItem> list = new ArrayList<ActivityItem>();
		
		NodeList activities = doc.getElementsByTagName("activityRecord");
		ActivityItem activityItem;
		Element element;
		String activity;
		String date;
		
		for(int i = 0; i < activities.getLength(); i++){
			element = (Element)activities.item(i);
			activity = getElementValue(element.getElementsByTagName("activity"));
			date = getElementValue(element.getElementsByTagName("date"));
			
			activityItem = new ActivityItem();
			activityItem.setActivity(activity);
			activityItem.setDate(date);
			
			list.add(activityItem);
		}
		
		return list;
	}
}
