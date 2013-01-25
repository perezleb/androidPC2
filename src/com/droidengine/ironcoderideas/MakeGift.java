package com.droidengine.ironcoderideas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MakeGift extends Activity {
	
	private static final String TOKEN_KEY = "CONS_ID";
	private static final String CONS_ID_KEY = "TOKEN";
	private static final String FR_ID_KEY = "FR_ID";

	private String token;
	private String consID;
	private String frID;
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_gift);
        
        token = getIntent().getStringExtra(TOKEN_KEY);
        consID = getIntent().getStringExtra(CONS_ID_KEY);
        frID = getIntent().getStringExtra(FR_ID_KEY);

    }
	
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.nav_menu, menu);
	    return true;
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
			return true;
		} else if (item.getItemId() == R.id.logout) {
			// logout
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void startProgressActivity(){
		Intent intent = new Intent(this, Progress.class);
    	startActivity(intent);
	}
	
	private void startEmailActivity(){
		Intent intent = new Intent(this, Email.class);
    	startActivity(intent);
	}
 
}
