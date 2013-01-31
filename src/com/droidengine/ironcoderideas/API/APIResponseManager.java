package com.droidengine.ironcoderideas.API;

import java.io.InputStream;

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
	
	public boolean isErrorMessage(){
		if (doc.getElementsByTagName("errorResponse").getLength() > 0){	    	
	    	Element errorResponse = (Element) doc.getElementsByTagName("errorResponse").item(0);
	    	NodeList errorTag = errorResponse.getElementsByTagName("message");
	    	if (errorTag.getLength() == 1){
	    		NodeList errorNode = errorTag.item(0).getChildNodes();
	    		if (errorNode.getLength() == 1){
	    			errorMessage = errorNode.item(0).getNodeValue();
	    			Log.d(TAG, errorMessage);
	    		}
	    	}
	    	return true;
	    }
		return false;		
	}
	
	public String getElementValue(NodeList list){
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
