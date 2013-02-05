package com.droidengine.ironcoderideas;

import com.droidengine.ironcoderideas.API.PCLoginAPI;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

public class PCLoginActivity extends Activity implements OnClickListener{
	
	private static final String TAG = "IRONCODER";
	private static final String CONS_ID_KEY = "CONS_ID";
	private static final String TOKEN_KEY = "TOKEN";
	
	private static final String LOGIN_ERROR_KEY = "ERROR";
	
	private PCLoginAPI pcLogin;
	
	public EditText usernameEditText;
	public EditText passwordEditText;
	public Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pc_login);
        
        String loginError = getIntent().getStringExtra(LOGIN_ERROR_KEY);        
        
        usernameEditText = (EditText)findViewById(R.id.username);
        passwordEditText = (EditText)findViewById(R.id.password);
        
        loginButton = (Button)findViewById(R.id.login_button);
        loginButton.setOnClickListener(this);
        
        if (loginError != null){
        	displayErrorDialog("Could not log you in");
        }
    }
    
    @Override
    protected void onRestart(){
    	super.onRestart();
    	Intent intent = new Intent(this, PCLoginActivity.class);
    	startActivity(intent);
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
			displayErrorDialog("Missing username or password");
			return;
		}
		
		pcLogin = new PCLoginAPI(username, password);
		
		try {
			pcLogin.login();
		} catch (Exception e) {	
			displayErrorDialog(e.toString().substring(e.toString().indexOf(":") + 2, e.toString().length()));
			return;
		}	
				
		Intent intent = new Intent(this, RegisteredTeamraisers.class);
    	intent.putExtra(CONS_ID_KEY, pcLogin.consID);
    	intent.putExtra(TOKEN_KEY, pcLogin.token);
    	startActivity(intent);
	}
    
	
	private void displayErrorDialog(String errorMessage){
		Log.d(TAG, "TODO: Show Error Dialog");
		
		AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);		
		alt_bld.setMessage(errorMessage).setCancelable(false)
		.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {				
				onRestart();
			}
		});

		AlertDialog alert = alt_bld.create();
		alert.show();
	}
}
