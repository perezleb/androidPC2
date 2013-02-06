package com.droidengine.ironcoderideas;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;

public class AbstractPCActivity extends FragmentActivity{
	protected static final String TAG = "IRONCODER";
	protected static final String TOKEN_KEY = "TOKEN";
	protected static final String CONS_ID_KEY = "CONS_ID";
	protected static final String FR_ID_KEY = "FR_ID";
	protected static final String LOGIN_ERROR_KEY = "ERROR";
	
	protected String token;
	protected String consID;
	protected String frID;
	
	@SuppressLint("NewApi")
	public void buildActionBar(){
		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= 11){
        	ActionBar actionBar = getActionBar();
    		if(actionBar != null){
    			actionBar.setDisplayShowHomeEnabled(false);
    		}
        	
        }
	}
	
	public void getConstituent(Intent intent){
		token = intent.getStringExtra(TOKEN_KEY);
        consID = intent.getStringExtra(CONS_ID_KEY);
        frID = intent.getStringExtra(FR_ID_KEY);
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.progress) {
			// do nothing
			startProgressActivity();
			return true;
		} else if (item.getItemId() == R.id.email) {
			//go to email page
			startEmailActivity();
			return true;
		} else if (item.getItemId() == R.id.make_gift) {
			//go to make gift page
			startGiftActivity();
			return true;
		} else if (item.getItemId() == R.id.logout) {
			// logout
			startLoginActivity();
			return true;
			
		} else if (item.getItemId() == R.id.my_teamraisers){
			startRegisteredTeamraisersActivity();
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}
	
	public void startLoginActivity(){
		Intent intent = new Intent(this, PCLoginActivity.class);
		startActivity(intent);
	}
	
	public void startEmailActivity(){
		Intent intent = new Intent(this, Email.class);
		intent.putExtra(CONS_ID_KEY, consID);
    	intent.putExtra(TOKEN_KEY, token);
    	intent.putExtra(FR_ID_KEY, frID);
    	startActivity(intent);
	}
	
	public void startGiftActivity(){
		Intent intent = new Intent(this, MakeGift.class);
		intent.putExtra(CONS_ID_KEY, consID);
    	intent.putExtra(TOKEN_KEY, token);
    	intent.putExtra(FR_ID_KEY, frID);
    	startActivity(intent);
	}
	
	public void startRegisteredTeamraisersActivity(){
		Intent intent = new Intent(this, RegisteredTeamraisers.class);
		intent.putExtra(CONS_ID_KEY, consID);
    	intent.putExtra(TOKEN_KEY, token);
    	startActivity(intent);
	}
	
	public void startProgressActivity(){
		Intent intent = new Intent(this, Progress.class);
		intent.putExtra(CONS_ID_KEY, consID);
    	intent.putExtra(TOKEN_KEY, token);
    	intent.putExtra(FR_ID_KEY, frID);
    	startActivity(intent);
	}

}
