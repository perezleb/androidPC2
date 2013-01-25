package com.droidengine.ironcoderideas;

import java.util.ArrayList; 

import com.droidengine.ironcoderideas.ListAdapters.RecentActivityListBaseAdapter;
import com.droidengine.ironcoderideas.ListItems.ActivityItem;
import com.droidengine.ironcoderideas.ListItems.ActivityListHeader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

public class Progress extends Activity {
	
	private static final String TAG = "IRONCODER";
	
	private static final String TOKEN_KEY = "CONS_ID";
	private static final String CONS_ID_KEY = "TOKEN";
	private static final String FR_ID_KEY = "FR_ID";
	
	
	private String token;
	private String consID;
	private String frID;
	
	private ArrayList activityList;
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        token = getIntent().getStringExtra(TOKEN_KEY);
        consID = getIntent().getStringExtra(CONS_ID_KEY);
        frID = getIntent().getStringExtra(FR_ID_KEY);
        
        if (token != null && consID != null){
        	activityList = getRecentActivity();
        	
        } else {
        	Log.d(TAG, "token or cons_id is null");
        	//TODO show error message
        }
        
        setContentView(R.layout.progress);
        
        final ListView lv1 = (ListView) findViewById(R.id.donor_list);
        lv1.setAdapter(new RecentActivityListBaseAdapter(this, activityList));
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
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void startEmailActivity(){
		Intent intent = new Intent(this, Email.class);
    	startActivity(intent);
	}
	
	private void startGiftActivity(){
		Intent intent = new Intent(this, MakeGift.class);
    	startActivity(intent);
	}
	
	private ArrayList<ActivityItem> getRecentActivity(){
		ArrayList list = new ArrayList();
		
		ActivityListHeader header = new ActivityListHeader();
		header.setHeader("Recent Donors");
		list.add(header);
		
		ActivityItem item = new ActivityItem();
		item.setDonor("Kyle", "Martinez", "$32.00");
		list.add(item);
		
		item = new ActivityItem();
		item.setDonor("Kyle", "Martinez", "$32.00");
		list.add(item);
		item = new ActivityItem();
		item.setDonor("Kyle", "Martinez", "$32.00");
		list.add(item);
		item = new ActivityItem();
		item.setDonor("Kyle", "Martinez", "$32.00");
		list.add(item);
		item = new ActivityItem();
		item.setDonor("Kyle", "Martinez", "$32.00");
		list.add(item);

		return list;
	}
 
}
