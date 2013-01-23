package com.droidengine.ironcoderideas.API;

import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Element;
import android.util.Log;

public class PCLoginAPI extends APIResponseManager {
	private static String TAG = "IRONCODER";
	private static String METHOD = "login";
	private static String API = "CRConsAPI?";
	
	private String username;
	private String password;
	
	public PCLoginAPI(String loginName, String loginPassword){
		username = loginName;
		password = loginPassword;
	}
	
	public void login() throws Exception{
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("user_name", username);
		params.put("password", password);
		
		//Make API request to login user
		APIController apiUser = new APIController(API, METHOD, params);
		response = apiUser.post();		
		
		if (response == null){
			throw new Exception("API Response Error");
		}
		
		//Parse response into document
		DocumentBuilder db = getDocumentBuilder();
		if (db != null)
			doc = db.parse(response);
		else
			throw new Exception("Error creating document builder");		
		
	    //check for errorResponse
		if (isErrorMessage()){
			if (errorMessage != null)
				throw new Exception(errorMessage);
			else
				throw new Exception("Technical Difficulties");
		}
	    
	    //get token and cons_id
	    Element loginResponse = (Element) doc.getElementsByTagName("loginResponse").item(0);
	    token = loginResponse.getElementsByTagName("token").item(0).getChildNodes().item(0).getNodeValue();
	    consID = loginResponse.getElementsByTagName("cons_id").item(0).getChildNodes().item(0).getNodeValue();	    
	    
	    if (token == null || consID == null){
	    	throw new Exception("Technical Difficulties: Could not log you in.");
	    }
	    
	    apiUser.shutDown();
	}
	
}
