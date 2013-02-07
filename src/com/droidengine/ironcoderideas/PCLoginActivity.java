package com.droidengine.ironcoderideas;

import com.droidengine.ironcoderideas.API.PCLoginAPI;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;

import com.google.android.gcm.GCMRegistrar;
import com.droidengine.ironcoderideas.R;

import static com.droidengine.ironcoderideas.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static com.droidengine.ironcoderideas.CommonUtilities.EXTRA_MESSAGE;
import static com.droidengine.ironcoderideas.CommonUtilities.SENDER_ID;

public class PCLoginActivity extends Activity implements OnClickListener{
	
	private static final String TAG = "IRONCODER";
	private static final String CONS_ID_KEY = "CONS_ID";
	private static final String TOKEN_KEY = "TOKEN";
	
	/**
     * Intent's extra that contains the message to be displayed.
     */
    static final String EXTRA_MESSAGE = "message";
	
	private static final String LOGIN_ERROR_KEY = "ERROR";
	
	private PCLoginAPI pcLogin;
	
	private final BroadcastReceiver handleMessageReceiver =
            new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
            Log.d(TAG, newMessage + "\n");
        }
    };
	    
    AsyncTask<Void, Void, Void> registerTask;
	
    public EditText usernameEditText;
	public EditText passwordEditText;
	public Button loginButton;

    @SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);
        
        setContentView(R.layout.pc_login);
        
        registerGCM();
        
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= 11){
        	ActionBar actionBar = getActionBar();
    		if(actionBar != null){
    			actionBar.setDisplayShowHomeEnabled(false);
    		}
        	
        }
        
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
		
//		username = "kmartinez";
//		password = "kmartinez";
								
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
	
	// Register for Google Cloud Messaging
	private void registerGCM()
	{
		//GCMRegistrar.unregister(this);
		
		Log.d(TAG, "GCM - in registerGCM() \n");
		registerReceiver(handleMessageReceiver,
                new IntentFilter(DISPLAY_MESSAGE_ACTION));
        final String regId = GCMRegistrar.getRegistrationId(this);
        Log.d(TAG, "GCM - regId = " + regId + "\n");
        if (regId.equals("")) {
            // Automatically registers application on startup.
            GCMRegistrar.register(this, SENDER_ID);
            Log.d(TAG, "GCM - registered \n");
        } else {
            // Device is already registered on GCM, check server.
            if (GCMRegistrar.isRegisteredOnServer(this)) {
                // Skips registration.
            	Log.d(TAG, getString(R.string.already_registered) + "\n");
            } else {
            	Log.d(TAG, "GCM - trying to register again..." + "\n");
                // Try to register again, but not in the UI thread.
                // It's also necessary to cancel the thread onDestroy(),
                // hence the use of AsyncTask instead of a raw thread.
                final Context context = this; 
                registerTask = new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {
                        boolean registered =
                                ServerUtilities.register(context, regId);
                        // At this point all attempts to register with the app
                        // server failed, so we need to unregister the device
                        // from GCM - the app will try to register again when
                        // it is restarted. Note that GCM will send an
                        // unregistered callback upon completion, but
                        // GCMIntentService.onUnregistered() will ignore it.
                        if (!registered) {
                            GCMRegistrar.unregister(context);
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        registerTask = null;
                    }

                };
                registerTask.execute(null, null, null);
            }
        }

	}
	
	@Override
    protected void onDestroy() {
        if (registerTask != null) {
            registerTask.cancel(true);
        }
        unregisterReceiver(handleMessageReceiver);

        GCMRegistrar.unregister(this);
        GCMRegistrar.onDestroy(this);
        super.onDestroy();
    }
}
