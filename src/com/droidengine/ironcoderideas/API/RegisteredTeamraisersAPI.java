package com.droidengine.ironcoderideas.API;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import com.droidengine.ironcoderideas.ListItems.TeamraiserItem;

import android.util.Log;

public class RegisteredTeamraisersAPI extends APIResponseManager {
	private static String TAG = "IRONCODER";
	private static String METHOD = "getRegisteredTeamraisers";
	private static String API = "CRTeamraiserAPI?";
	
	public String consID;
	
	private ArrayList<TeamraiserItem> consTeamraisers;
	
	
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
		
		consTeamraisers = getRegisteredTeamraiserList();
								
		apiUser.shutDown();		
		return consTeamraisers;
		
	}
	
	private ArrayList<TeamraiserItem> getRegisteredTeamraiserList(){

		ArrayList<TeamraiserItem> list = new ArrayList<TeamraiserItem>();
		
		NodeList teamraisers = doc.getElementsByTagName("teamraiser");
		String name;
		String location;
		String id;
		Element element;		
		TeamraiserItem teamraiser;
		
		for(int i = 0; i < teamraisers.getLength(); i++){
			element = (Element)teamraisers.item(i);
			name = getElementValue(element.getElementsByTagName("name"));
			location = getElementValue(element.getElementsByTagName("location_name"));			
			id = getElementValue(element.getElementsByTagName("id"));
			
			teamraiser = new TeamraiserItem();
			teamraiser.setTeamraiserName(name);
			teamraiser.setTeamraiserLocation(location);
			teamraiser.setTeamraiserID(id);
			list.add(teamraiser);
		}
		
		return list;
	}
	
	private String getElementValue(NodeList list){
		if (list.getLength() == 0){
			return null;
		} else {
			NodeList childNodeList = list.item(0).getChildNodes();
			if (childNodeList.getLength() == 0){
				return null;
			} else {
				return childNodeList.item(0).getNodeValue();
			}
		}
	}
}
