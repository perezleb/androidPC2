package com.droidengine.ironcoderideas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class Email extends Activity {
	
	private static final String TOKEN_KEY = "CONS_ID";
	private static final String CONS_ID_KEY = "TOKEN";
	private static final String FR_ID_KEY = "FR_ID";

	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email);
    }
	
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.nav_menu, menu);
	    return true;
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
		case R.id.progress:
			// do nothing
			startProgressActivity();
			return true;
		case R.id.email:
			//go to email page
			return true;
		case R.id.make_gift:
			//go to make gift page
			startGiftActivity();
			return true;
		case R.id.logout:
			// logout
			return true;
		default:
            return super.onOptionsItemSelected(item);
		}
	}
 
	private void startProgressActivity(){
		Intent intent = new Intent(this, Progress.class);
    	startActivity(intent);
	}
	
	private void startGiftActivity(){
		Intent intent = new Intent(this, MakeGift.class);
    	startActivity(intent);
	}
}
