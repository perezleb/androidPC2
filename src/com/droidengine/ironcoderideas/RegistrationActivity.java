package com.droidengine.ironcoderideas;

import org.apache.http.util.EncodingUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class RegistrationActivity extends Activity {
	private static final String TOKEN_KEY = "TOKEN";
	private static final String CONS_ID_KEY = "CONS_ID";

	private static final String URL = "URL";

	private String token;
	private String consID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration);

		token = getIntent().getStringExtra(TOKEN_KEY);
		consID = getIntent().getStringExtra(CONS_ID_KEY);

		String url = getIntent().getStringExtra(URL);
		String postData = "sso_auth_token=" + token;

		WebView webView = (WebView) findViewById(R.id.webview);
		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setSupportZoom(true);
		webSettings.setBuiltInZoomControls(true);

		webView.setWebViewClient((new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView wView, String url) {
				if (url.contains("pg=center")) {
					startRegisteredTeamraisersActivity();
				} else {
					wView.loadUrl(url);
				}
				return true;
			}
		}));

		String fullUrl = url + "&" + postData;

		webView.postUrl(fullUrl, EncodingUtils.getBytes(postData, "BASE64"));
	}

	private void startRegisteredTeamraisersActivity() {
		Intent intent = new Intent(this, RegisteredTeamraisers.class);
		intent.putExtra(CONS_ID_KEY, consID);
		intent.putExtra(TOKEN_KEY, token);
		startActivity(intent);
	}

}
