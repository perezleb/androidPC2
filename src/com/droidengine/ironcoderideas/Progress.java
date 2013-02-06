package com.droidengine.ironcoderideas;

import java.util.ArrayList; 

import com.droidengine.ironcoderideas.API.GetParticipantProgressAPI;
import com.droidengine.ironcoderideas.API.RecentActivityAPI;
import com.droidengine.ironcoderideas.ListAdapters.RecentActivityListBaseAdapter;
import com.droidengine.ironcoderideas.ListItems.ActivityItem;
import com.droidengine.ironcoderideas.ListItems.ActivityListHeader;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

public class Progress extends AbstractPCActivity {
	


	private GetParticipantProgressAPI participantProgress;
	private TextView amountRaised;
	private TextView myGoal;
	private TextView daysLeft;
	
	private RecentActivityAPI activityAPI;
	private ArrayList activityList;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        getConstituent(getIntent());
        
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
                
        buildActionBar();
        
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
	
	@Override
	protected void onRestart(){
		Log.d(TAG, "Progress: onRestart");
		super.onRestart();
		startActivity(getIntent());
	}
		
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.nav_menu, menu); 
	    menu.removeItem(R.id.progress);
	    return true;
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
