package com.droidengine.ironcoderideas.API;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;


import android.util.Log;

public class APIController {
	private static String TAG = "IRONCODER";
	
	private static String URL = "http://qa217.bvt2.corp.convio.com/site/";
	private static String KEY = "api_key";
	private static String VERSION = "1.0";
	
	private String api;
	private String method;
	private HashMap<String, String> arguments;
	
	private HttpClient client;
	
	private InputStream xmlResponse;
	
	
	public APIController(String api, String method, HashMap<String, String> params){		
		this.api = api;
		this.method = method;
		this.arguments = params;
	}
	
	public InputStream get(){
		ExecutorService exs = Executors.newSingleThreadExecutor();
		try {
			return exs.submit(new Callable<InputStream>() {
				public InputStream call() throws UnsupportedEncodingException {
					String request = generateRequest();
					client = new DefaultHttpClient();
					HttpGet get = new HttpGet(request);		
					try {					
						HttpResponse response = client.execute(get);
						xmlResponse = response.getEntity().getContent();						
						//readResponse();
					} catch(Exception e){
						Log.d(TAG, e.toString());
					}
					return xmlResponse;
				}
			}).get();
		}
		catch(ExecutionException e) {
			Log.d(TAG, e.toString());
		}
		catch(InterruptedException e) {
			Log.d(TAG, e.toString());
		}
		
		return null;
	}
	
	public InputStream post() {
		ExecutorService exs = Executors.newSingleThreadExecutor();
		try {
			return exs.submit(new Callable<InputStream>() {
				public InputStream call() throws UnsupportedEncodingException {
					String request = generateRequest();
					client = new DefaultHttpClient();										
					HttpPost post = new HttpPost(request);										
					try {					
						HttpResponse response = client.execute(post);
						xmlResponse = response.getEntity().getContent();												
					} catch(Exception e){
						Log.d(TAG, e.toString());
					}
					return xmlResponse;
				}
			}).get();
		}
		catch(ExecutionException e) {
			Log.d(TAG, e.toString());
		}
		catch(InterruptedException e) {
			Log.d(TAG, e.toString());
		}
		
		return null;
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
	
}
