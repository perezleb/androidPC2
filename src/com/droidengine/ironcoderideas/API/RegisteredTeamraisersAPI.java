package com.droidengine.ironcoderideas.API;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.droidengine.ironcoderideas.ListItems.TRGPSConstants;
import com.droidengine.ironcoderideas.ListItems.TeamraiserItem;

import android.util.Log;

public class RegisteredTeamraisersAPI extends APIResponseManager {
	private static String TAG = "IRONCODER";
	private static String METHOD = "getRegisteredTeamraisers";
	private static String API = "CRTeamraiserAPI?";
	
	public String consID;
	
	public RegisteredTeamraisersAPI(String cons){
		consID = cons;
	}
	
	public ArrayList<TeamraiserItem> getRegisteredTeamraisers() throws Exception {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("cons_id", consID);
		
		//Make API request to login user
		APIController apiUser = new APIController(API, METHOD, params);
		apiUser.execute();
		doc = apiUser.get();

	    //check for errorResponse
		if (isErrorMessage()){
			Log.d(TAG, "Error Message in Response");
			throw new Exception(errorMessage);
		}
		
		return getRegisteredTeamraiserList();		
	}
	
	private ArrayList<TeamraiserItem> getRegisteredTeamraiserList(){

		ArrayList<TeamraiserItem> list = new ArrayList<TeamraiserItem>();
		
		NodeList teamraisers = doc.getElementsByTagName("teamraiser");
		String name;
		String teamName;
		String location;
		String id;
		Element element;		
		TeamraiserItem teamraiser;
		
		for(int i = 0; i < teamraisers.getLength(); i++){
			element = (Element)teamraisers.item(i);
			name = getElementValue(element.getElementsByTagName("name"));
			location = getElementValue(element.getElementsByTagName("location_name"));			
			id = getElementValue(element.getElementsByTagName("id"));
			teamName = getElementValue(element.getElementsByTagName("teamName"));
			
			teamraiser = new TeamraiserItem();
			teamraiser.setTeamraiserName(name);
			teamraiser.setTeamName(teamName);
			teamraiser.setTeamraiserLocation(location);
			teamraiser.setTeamraiserID(id);
			list.add(teamraiser);
		}
		
		return list;
	}
		
}
