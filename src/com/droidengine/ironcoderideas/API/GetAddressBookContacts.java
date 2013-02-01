package com.droidengine.ironcoderideas.API;

import java.util.ArrayList;
import java.util.HashMap;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import android.os.AsyncTask;

public class GetAddressBookContacts extends APIResponseManager{
	
	private static String TAG = "IRONCODER";
	private static String METHOD = "getAddressBookContacts";
	private static String API = "CRAddressBookAPI?";
	
	private String token;
	
	public GetAddressBookContacts(String auth){
		token = auth;
	}
	
	public ArrayList<Contact> getContacts() throws Exception{ 
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("sso_auth_token", token);
		
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
		
		return parseContactsWithEmail();
	}
	
	private ArrayList<Contact>parseContactsWithEmail(){
		ArrayList<Contact> list = new ArrayList<Contact>();
		
		NodeList contacts = doc.getElementsByTagName("addressBookContact");
		Contact contact;
		Element element;
		String name;
		String email;
		
		for(int i = 0; i < contacts.getLength(); i++){
			element = (Element)contacts.item(i);
			if (getElementValue(element.getElementsByTagName("email")) != null){
				name = getElementValue(element.getElementsByTagName("firstName")) + " " + getElementValue(element.getElementsByTagName("lastName"));
				email = getElementValue(element.getElementsByTagName("email"));
				
				contact = new Contact();
				contact.setName(name);
				contact.setEmail(email);
				list.add(contact);
			}
		}
		return list;
	}
		
	public class Contact{
		private String contactName;
		private String contactEmail;
		
		public void setName(String name){
			contactName = name;
		}
		
		public void setEmail(String email){
			contactEmail = email;
		}
		
		public String getName(){
			return contactName;
		}
		
		public String getEmail(){
			return contactEmail;
		}				
	}
}
