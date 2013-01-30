package com.droidengine.ironcoderideas.API;

import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.os.AsyncTask;

public class GetParticipantProgressAPI extends APIResponseManager{
	private static String TAG = "IRONCODER";
	private static String METHOD = "getParticipantProgress";
	private static String API = "CRTeamraiserAPI?";
	
	private String consID;
	private String frID;
	
	private String goal;
	private String amountRaised;
	private String daysLeft;
	
	public GetParticipantProgressAPI(String cons, String id){
		consID = cons;
		frID = id;
	}
	
	public void getProgress() throws Exception{
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("cons_id", consID);
		params.put("fr_id", frID);
		
		//Make API request to get participant progress
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
			
		// parse response
		NodeList personalProgress = doc.getElementsByTagName("personalProgress");
		Element element = (Element)personalProgress.item(0);
		goal = getElementValue(element.getElementsByTagName("goal"));
		amountRaised = getElementValue(element.getElementsByTagName("raised"));
		daysLeft = getElementValue(doc.getElementsByTagName("daysLeft"));
	}
	
	public String getGoal(){
		if (goal.equals("0")){
			goal = "$" + goal + ".00";
		} else {
			goal = "$" + goal.substring(0, goal.length() - 2) + "." + goal.substring(goal.length() - 2, goal.length());
		}
		return goal;
	}
	
	public String getAmountRaised(){
		if (amountRaised.equals("0")){
			amountRaised = "$" + amountRaised + ".00";
		} else {
			amountRaised = "$" + amountRaised.substring(0, amountRaised.length() - 2) + "." + amountRaised.substring(amountRaised.length() - 2, amountRaised.length());
		}
		return amountRaised;
	}
	
	public String getDaysLeft(){
		return daysLeft;
	}
	
}
