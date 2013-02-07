package com.droidengine.ironcoderideas.API;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;
/**
 * This is a more secure way of doing post calls to the apis. 
 * 
 * We may end up using this for all method calls
 * It's a little dirty right now, but the basic idea is there. 
 * 
 * @author leber.perez
 *
 */
public class SecureAPIController extends APIController {

	private static String URL = "https://bvt2-secure.convio.com/qa217/site/";

	
	public SecureAPIController(String api, String method,
			Map<String, String> params) {
		super(api, method, params);
	}
	
	@Override
	protected String getURL() {
		return URL;
	}
	
	@Override
	public InputStream doPost() throws UnsupportedEncodingException {
		String APIurl = generateUrl();
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(generateParameters(), "UTF-8");

		HttpPost post = new HttpPost(APIurl);
		post.setEntity(entity);
		
		Log.d(TAG, "executing request " + post.getRequestLine());
		
		try {
			
			HttpResponse response = client.execute(post);
			xmlResponse = response.getEntity().getContent();			
		} catch (Exception e) {
			Log.d(TAG, e.toString());
		}
		return xmlResponse;
	}
	
	protected String generateUrl(){
		String request = getURL() + api + "method=" + method + "&api_key=" + KEY + "&v=" + VERSION;
		return request;
	}
	
	protected List<BasicNameValuePair> generateParameters(){
		List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
		Iterator<Entry<String, String>> it = arguments.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        formparams.add(new BasicNameValuePair(pairs.getKey().toString(), pairs.getValue().toString()));
	    }
		return formparams;
		
	}

}
