package com.droidengine.ironcoderideas;

import com.droidengine.ironcoderideas.API.PCLoginAPI;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;

public class PCLoginActivity extends Activity implements OnClickListener{
	
	private static final String TAG = "IRONCODER";
	private static final String CONS_ID_KEY = "CONS_ID";
	private static final String TOKEN_KEY = "TOKEN";
	
	private PCLoginAPI pcLogin;
	
	public EditText usernameEditText;
	public EditText passwordEditText;
	public Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pc_login);
        
        usernameEditText = (EditText)findViewById(R.id.username);
        passwordEditText = (EditText)findViewById(R.id.password);
        
        loginButton = (Button)findViewById(R.id.login_button);
        loginButton.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.login_button) {
			login();
		}
		
	}    
	
	private void login(){
		Log.d(TAG, "Attempting to login to PC2");
		
		String username = usernameEditText.getText().toString();
		String password = passwordEditText.getText().toString();
		
		username = "kmartinez";
		password = "kmartinez";
		
		if (username.equals("") || password.equals("")){
			Log.d(TAG, "Missing Username or Password");
			return;
		}
		
		pcLogin = new PCLoginAPI(username, password);
		
//		ProgressDialog loadingDialog = ProgressDialog.show(PCLoginActivity.this, "", "Loading...", true); 
		
		try {
			pcLogin.login();
		} catch (Exception e) {	
			displayErrorDialog(e.toString());
		}
//		} finally {
//			loadingDialog.dismiss();
//		}
				
		Intent intent = new Intent(this, RegisteredTeamraisers.class);
    	intent.putExtra(CONS_ID_KEY, pcLogin.consID);
    	intent.putExtra(TOKEN_KEY, pcLogin.token);
    	startActivity(intent);
	}
    
	
	private void displayErrorDialog(String errorMessage){
		Log.d(TAG, "TODO: Show Error Dialog");
	}
}
