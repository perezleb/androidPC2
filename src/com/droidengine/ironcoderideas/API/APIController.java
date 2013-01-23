package com.droidengine.ironcoderideas.API;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;

import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import android.os.AsyncTask;
import android.util.Log;

public class APIController extends AsyncTask<Void, Void,  Document> {
	
	private static String TAG = "IRONCODER";
	
	private static String URL = "http://qa217.bvt2.corp.convio.com/site/";
	private static String KEY = "api_key";
	private static String VERSION = "1.0";
	
	private String api;
	private String method;
	private HashMap<String, String> arguments;
	
	private HttpClient client = new DefaultHttpClient();
	
	private InputStream xmlResponse;
	
		
	
	public APIController(String api, String method, HashMap<String, String> params){		
		this.api = api;
		this.method = method;
		this.arguments = params;
	}
	
	public InputStream doGet() {
		String request = generateRequest();
		HttpGet get = new HttpGet(request);
		try {
			HttpResponse response = client.execute(get);
			xmlResponse = response.getEntity().getContent();
		} catch (Exception e) {
			Log.d(TAG, e.toString());
		}
		return xmlResponse;

	}

	public InputStream doPost() {
		String APIurl = generateRequest();
		HttpPost post = new HttpPost(APIurl);
		try {
			HttpResponse response = client.execute(post);
			xmlResponse = response.getEntity().getContent();
		} catch (Exception e) {
			Log.d(TAG, e.toString());
		}
		return xmlResponse;
	}
	
	public void shutDown(){
		if (client != null){
			client.getConnectionManager().shutdown();
		}
	}
	
	private String generateRequest(){
		String request = URL + api + "method=" + method + "&api_key=" + KEY + "&v=" + VERSION;
		
		Iterator it = arguments.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        request += "&" + pairs.getKey() + "=" + pairs.getValue();
	    }
		
	    Log.d(TAG, request);
	    
		return request;
	}
	
	
	//Used for debuging api response
	private void readResponse(){
		BufferedReader br = new BufferedReader(new InputStreamReader(xmlResponse));
		String debugLine = "";
		try {
			while ((debugLine = br.readLine()) != null){
				Log.d(TAG, debugLine);
			}
		} catch (IOException e) {
			Log.d(TAG,  e.toString());
		}
	}

	@Override
	protected Document doInBackground(Void... arg0) {
		InputStream response = doPost();
		Document doc = null;
		if (response == null) {
			Log.d(TAG, "Error generating response");
		} else {

			// Parse response into document
			DocumentBuilder db = getDocumentBuilder();
			if (db != null)
				try {
					doc = db.parse(response);
				} catch (Exception e) {
					Log.d(TAG, "Unable to parse response: " + e.toString());
				}
			else
				Log.d(TAG, "Error creating document builder");
		}
		return doc;
	}
	
	   @Override
       protected void onPostExecute(Document result) {
		   Log.d(TAG, result.toString());
           super.onPostExecute(result);
           return;
       }
	   
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
	

	
	
}
