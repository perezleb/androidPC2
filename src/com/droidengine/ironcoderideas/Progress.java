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
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Progress extends Activity implements OnClickListener{
	
	private static final String TAG = "IRONCODER";
	
	private static final String TOKEN_KEY = "TOKEN";
	private static final String CONS_ID_KEY = "CONS_ID";
	private static final String FR_ID_KEY = "FR_ID";
	private static final String LOGIN_ERROR_KEY = "ERROR";
	
	private String token;
	private String consID;
	private String frID;
	
	private GetParticipantProgressAPI participantProgress;
	private TextView amountRaised;
	private TextView myGoal;
	private TextView daysLeft;
	
	private RecentActivityAPI activityAPI;
	private ArrayList activityList;
	
	private Button emailNavButton;
	private Button makeGiftNavButton;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        
		Log.d(TAG, "Progress: onCreate");
		super.onCreate(savedInstanceState);
        
        token = getIntent().getStringExtra(TOKEN_KEY);
        consID = getIntent().getStringExtra(CONS_ID_KEY);
        frID = getIntent().getStringExtra(FR_ID_KEY);
        
        if (token != null && consID != null & frID != null){
        	activityList = getRecentActivity();
        	participantProgress = getProgress();
        	
        } else {
        	Log.d(TAG, "token or cons_id is null");
        	// Kick back to login screen
        	
        	Intent intent = new Intent(this, PCLoginActivity.class);
        	intent.putExtra(LOGIN_ERROR_KEY, "ERROR");
        	
    		startActivity(intent);
        	return;
        }
        
        setContentView(R.layout.progress);
        
        amountRaised = (TextView)findViewById(R.id.amount_raised);
        myGoal = (TextView)findViewById(R.id.fundraising_goal);
        daysLeft = (TextView)findViewById(R.id.days_left);
        
        emailNavButton = (Button)findViewById(R.id.email_nav_button);
        emailNavButton.setOnClickListener(this);
        
        makeGiftNavButton = (Button)findViewById(R.id.make_gift_nav_button);
        makeGiftNavButton.setOnClickListener(this);
        
        if (participantProgress != null){
        	setAmountRaised(participantProgress.getAmountRaised());
        	setGoal(participantProgress.getGoal());
        	setDaysLeft(participantProgress.getDaysLeft());
        }
        
        final ListView lv1 = (ListView) findViewById(R.id.donor_list);
        lv1.setAdapter(new RecentActivityListBaseAdapter(this, activityList));
                
    }	
	
	
	@Override
	protected void onRestart(){
		Log.d(TAG, "Progress: onRestart");
		super.onRestart();
		startActivity(getIntent());
	}
		
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.nav_menu, menu);
	    return true;
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.logout) {
			// do nothing
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

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.email_nav_button){
			startEmailActivity();
		}
		else if (v.getId() == R.id.make_gift_nav_button){
			startGiftActivity();
		}		
	}
}
