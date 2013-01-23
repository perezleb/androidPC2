package com.droidengine.ironcoderideas.API;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.util.Log;

public class APIResponseManager {
	
	private static final String TAG = "IRONCODER";
	
	public Document doc;
	
	public String token;
	public String consID;
	
	public InputStream response;
	public String errorMessage;
	
	
	public DocumentBuilder getDocumentBuilder(){
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    dbf.setValidating(false);
	    dbf.setIgnoringComments(false);
	    dbf.setIgnoringElementContentWhitespace(true);
	    dbf.setNamespaceAware(true);
	    
	    DocumentBuilder db = null;
	    
	    try {
			db = dbf.newDocumentBuilder();			
		} catch (ParserConfigurationException e) {
			Log.d(TAG, e.toString());
		}
	    	    
	    return db;
	}
	
	public boolean isErrorMessage(){
		if (doc.getElementsByTagName("errorResponse").getLength() > 0){	    	
	    	Element errorResponse = (Element) doc.getElementsByTagName("errorResponse").item(0);
	    	NodeList errorTag = errorResponse.getElementsByTagName("errorMessage");
	    	if (errorTag.getLength() == 1){
	    		NodeList errorNode = errorTag.item(0).getChildNodes();
	    		if (errorNode.getLength() == 1){
	    			errorMessage = errorNode.item(0).getNodeValue();
	    		}
	    	}
	    	return true;
	    }
		return false;		
	}
}
