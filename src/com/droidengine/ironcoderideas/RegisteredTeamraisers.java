package com.droidengine.ironcoderideas;

import java.util.ArrayList;

import com.droidengine.ironcoderideas.API.RegisteredTeamraisersAPI;
import com.droidengine.ironcoderideas.ListAdapters.RegisteredTeamraiserListAdapter;
import com.droidengine.ironcoderideas.ListItems.FindTeamraisersItem;
import com.droidengine.ironcoderideas.ListItems.TeamraiserItem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class RegisteredTeamraisers extends Activity{
	
	private static final String TAG = "IRONCODER";
	
	private static final String TOKEN_KEY = "TOKEN";
	private static final String CONS_ID_KEY = "CONS_ID";
	private static final String FR_ID_KEY = "FR_ID";
	private static final String COOKIE_KEY = "COOKIE";
	
	private String token;
	private String consID;
	private String cookie;
	private String frID;
	
	private RegisteredTeamraisersAPI regEvents;
	
	private ArrayList teamraiserList;
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        token = getIntent().getStringExtra(TOKEN_KEY);
        consID = getIntent().getStringExtra(CONS_ID_KEY);
        cookie = getIntent().getStringExtra(COOKIE_KEY);
        frID =  getIntent().getStringExtra(FR_ID_KEY);
        
        if (token != null && consID != null && cookie != null){
        	teamraiserList = getTeamraiserList();
        	
        } else {
        	Log.d(TAG, "token or cons_id is null");
        	//TODO show error message
        }
        
        setContentView(R.layout.registered_teamraisers);
        
        final ListView lv1 = (ListView) findViewById(R.id.registered_teamraisers_list);
        lv1.setAdapter(new RegisteredTeamraiserListAdapter(this, teamraiserList));
        
        lv1.setOnItemClickListener(new OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView<?> a, View v, int position, long id) { 
        		
        		if (position == teamraiserList.size() - 1){
        			openFindTeamraiserPage();
        		} else {
        			openProgressPage(position);
        		}
        	}  
        });

    }
	
	private void openProgressPage(int position){
		TeamraiserItem teamraiser = (TeamraiserItem) teamraiserList.get(position);
		String fr_id = teamraiser.getTeamraiserID();
		
		Intent intent = new Intent(this, Progress.class);
    	intent.putExtra(CONS_ID_KEY, consID);
    	intent.putExtra(TOKEN_KEY, token);
    	intent.putExtra(FR_ID_KEY, fr_id);
    	intent.putExtra(COOKIE_KEY, cookie);
    	startActivity(intent);

	}
	
	private void openFindTeamraiserPage(){
		Intent intent = new Intent(this, NearbyTeamraisersActivity.class);
    	startActivity(intent);
		
	}
	
	private ArrayList getTeamraiserList(){
		
		if (regEvents == null){
			regEvents = new RegisteredTeamraisersAPI(consID, cookie);
		}
		
		ArrayList list = new ArrayList();
		
		try {
			list = regEvents.getRegisteredTeamraisers();									
		}
		catch(Exception e){
			Log.d(TAG, e.toString());
		}
		
		FindTeamraisersItem findEventItem = new FindTeamraisersItem();
		list.add(findEventItem);
		
		return list;
	}

}
