package com.droidengine.ironcoderideas;

import org.apache.http.util.EncodingUtils;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class RegistrationActivity extends Activity {
	private static final String TOKEN_KEY = "TOKEN";
	private static final String URL = "URL";

	private String token;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration);

		token = getIntent().getStringExtra(TOKEN_KEY);

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
					finish();
				} else {
					wView.loadUrl(url);
				}
				return true;
			}
		}));

		String fullUrl = url + "?" + postData;

		webView.loadUrl(fullUrl);

	}

}
