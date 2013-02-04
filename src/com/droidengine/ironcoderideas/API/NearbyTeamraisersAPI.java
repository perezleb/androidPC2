package com.droidengine.ironcoderideas.API;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.droidengine.ironcoderideas.ListItems.TeamraiserItem;

import android.os.AsyncTask;

public class NearbyTeamraisersAPI extends APIResponseManager {

	private static String TAG = "IRONCODER";
	private static String METHOD = "getTeamraisersByDistance";
	private static String API = "CRTeamraiserAPI?";
	
	private static String DISTANCE = "10";
	private static String DISTANCE_UNITS = "mi";
	private static String LIST_ASCENDING = "true";
	private static String SORT_COLUMN = "distance";
	
	private String zip;
	
		
	public NearbyTeamraisersAPI (String zip){
		this.zip = zip;
	}
	
	public List getNearbyTeamraisers() throws Exception{
		
		APIController controller = new APIController(API, METHOD, buildParams());
		
		AsyncTask<Void, Void, Document>  task = controller.execute();
		doc = task.get();
		//check for errorResponse
		if (isErrorMessage()){
			if (errorMessage != null)
				throw new Exception(errorMessage);
			else
				throw new Exception("Technical Difficulties");
		}
		
		return buildTeamraiserList();
			

		
	}
	
	
	private List<TeamraiserItem> buildTeamraiserList() throws ParseException{
		ArrayList<TeamraiserItem> list = new ArrayList<TeamraiserItem>();
		
		NodeList teamraisers = doc.getElementsByTagName("teamraiser");
		String name;
		String location;
		String id;
		
		String streetAddress;
		String city;
		String state;
		String zip;
		String country;
		String date;
		String url;
		
		Element element;		
		TeamraiserItem teamraiser;
		
		for(int i = 0; i < teamraisers.getLength(); i++){
			element = (Element)teamraisers.item(i);
			name = getElementValue(element.getElementsByTagName("name"));
			location = getElementValue(element.getElementsByTagName("location_name"));			
			id = getElementValue(element.getElementsByTagName("id"));
			
			streetAddress = getElementValue(element.getElementsByTagName("street_address"));
			city = getElementValue(element.getElementsByTagName("city"));
			state = getElementValue(element.getElementsByTagName("state"));
			zip = getElementValue(element.getElementsByTagName("zip"));
			country = getElementValue(element.getElementsByTagName("country"));			
			date = getElementValue(element.getElementsByTagName("event_date"));		
			
			url = getElementValue(element.getElementsByTagName("greeting_url"));	
			
			teamraiser = new TeamraiserItem();
			
			teamraiser.setTeamraiserName(name);
			teamraiser.setTeamraiserLocation(location);
			teamraiser.setTeamraiserID(id);
			
			teamraiser.setStreetAddress(streetAddress);
			teamraiser.setCity(city);
			teamraiser.setState(state);
			teamraiser.setZip(zip);
			teamraiser.setCountry(country);
			teamraiser.setUrl(url);
			
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
			teamraiser.setDate(formatter.parse(date));
			
			list.add(teamraiser);
		}
		
		return list;
	}
	
	
	
	protected Map<String, String> buildParams(){
		HashMap<String, String> params = new HashMap<String, String>();
		
		params.put("starting_postal", zip);
		params.put("distance_units", DISTANCE_UNITS);
		params.put("search_distance", DISTANCE);
		params.put("list_sort_column", SORT_COLUMN);
		params.put("list_ascending", LIST_ASCENDING);

		return params;
	}
	
	
 }
