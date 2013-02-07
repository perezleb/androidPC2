package com.droidengine.ironcoderideas.API;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import android.os.AsyncTask;
import android.util.Log;

public class NotificationTask extends AsyncTask<String, Void, Void> {

	private static String TAG = "IRONCODER";
	private static String URL = "http://172.20.7.181:8080/gcm-demo/sendAll";

	@Override
	protected Void doInBackground(String... arg0) {
		String name = arg0[0];
		String amount = arg0[1];
		
		
		try {
			doPost( name + " just donated " + amount + " !!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.d(TAG, e.getMessage());
		}
		return null;
	}

	public void doPost(String message) throws Exception   {
		
		
		List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
		formparams.add(new BasicNameValuePair("message", message));
		
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(URL);
		post.setEntity(entity);

		Log.d(TAG, "executing request " + post.getRequestLine());

		
		HttpResponse response = client.execute(post);		
		Log.d(TAG, response.getEntity().getContent().toString());
	}


}
