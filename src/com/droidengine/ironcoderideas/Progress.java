package com.droidengine.ironcoderideas;

import java.util.ArrayList; 

import com.droidengine.ironcoderideas.API.GetParticipantProgressAPI;
import com.droidengine.ironcoderideas.API.RecentActivityAPI;
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
import android.widget.TextView;

public class Progress extends Activity {
	
	private static final String TAG = "IRONCODER";
	
	private static final String TOKEN_KEY = "TOKEN";
	private static final String CONS_ID_KEY = "CONS_ID";
	private static final String FR_ID_KEY = "FR_ID";
	
	private String token;
	private String consID;
	private String frID;
	
	private GetParticipantProgressAPI participantProgress;
	private TextView amountRaised;
	private TextView myGoal;
	private TextView daysLeft;
	
	private RecentActivityAPI activityAPI;
	private ArrayList activityList;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        token = getIntent().getStringExtra(TOKEN_KEY);
        consID = getIntent().getStringExtra(CONS_ID_KEY);
        frID = getIntent().getStringExtra(FR_ID_KEY);
        
        if (token != null && consID != null & frID != null){
        	activityList = getRecentActivity();
        	participantProgress = getProgress();
        	
        } else {
        	Log.d(TAG, "token or cons_id is null");
        	//TODO show error message
        }
        
        setContentView(R.layout.progress);
        
        amountRaised = (TextView)findViewById(R.id.amount_raised);
        myGoal = (TextView)findViewById(R.id.fundraising_goal);
        daysLeft = (TextView)findViewById(R.id.days_left);
        
        if (participantProgress != null){
        	setAmountRaised(participantProgress.getAmountRaised());
        	setGoal(participantProgress.getGoal());
        	setDaysLeft(participantProgress.getDaysLeft());
        }
        
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
			startLoginActivity();
			return true;
			
		} else if (item.getItemId() == R.id.my_teamraisers){
			startRegisteredTeamraisersActivity();
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void startLoginActivity(){
		Intent intent = new Intent(this, PCLoginActivity.class);
		startActivity(intent);
	}
	
	private void startEmailActivity(){
		Intent intent = new Intent(this, Email.class);
		intent.putExtra(CONS_ID_KEY, consID);
    	intent.putExtra(TOKEN_KEY, token);
    	intent.putExtra(FR_ID_KEY, frID);
    	startActivity(intent);
	}
	
	private void startGiftActivity(){
		Intent intent = new Intent(this, MakeGift.class);
		intent.putExtra(CONS_ID_KEY, consID);
    	intent.putExtra(TOKEN_KEY, token);
    	intent.putExtra(FR_ID_KEY, frID);
    	startActivity(intent);
	}
	
	private void startRegisteredTeamraisersActivity(){
		Intent intent = new Intent(this, RegisteredTeamraisers.class);
		intent.putExtra(CONS_ID_KEY, consID);
    	intent.putExtra(TOKEN_KEY, token);
    	startActivity(intent);
	}
	
	private GetParticipantProgressAPI getProgress(){
		GetParticipantProgressAPI progress = new GetParticipantProgressAPI(consID, frID);
		
		try{
			progress.getProgress();
		}catch(Exception e){
			Log.d(TAG, e.toString());
			return null;
		}
		
		return progress;
	}
	
	private ArrayList<ActivityItem> getRecentActivity(){
		if (activityAPI == null){
			activityAPI = new RecentActivityAPI(token, frID);
		}		
		
		ArrayList list = new ArrayList();
				
		try{
			list = activityAPI.getRecentActivity();
			
		} catch(Exception e){
			Log.d(TAG, e.toString());
		}
		
		ActivityListHeader header = new ActivityListHeader();
		header.setHeader("Recent Activity");
		list.add(0, header);

		return list;
	}
	
	private void setAmountRaised(String amount){
		if (amount != null){
			amountRaised.setText(amount);
		}
	}
	
	private void setGoal(String goal){
		if (goal != null){
			myGoal.setText(goal);
		}
	}
	
	private void setDaysLeft(String days){
		if (days != null){
			daysLeft.setText(days);
		}
	}
}
