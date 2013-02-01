package com.droidengine.ironcoderideas;

import java.util.ArrayList;

import com.droidengine.ironcoderideas.API.GetAddressBookContacts.Contact;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Email extends FragmentActivity implements OnClickListener, ContactDialog.NoticeDialogListener{
	private static final String TAG = "IRONCODER";
	
	private static final String TOKEN_KEY = "TOKEN";
	private static final String CONS_ID_KEY = "CONS_ID";
	private static final String FR_ID_KEY = "FR_ID";
	
	private String token;
	private String consID;
	private String frID;
	
	private Button toButton;
	private Button sendButton;
	private EditText sendToEditText;
	private EditText subjectEditText;
	private EditText messageBodyEditText;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email);
        
        token = getIntent().getStringExtra(TOKEN_KEY);
        consID = getIntent().getStringExtra(CONS_ID_KEY);
        frID = getIntent().getStringExtra(FR_ID_KEY);
        
        sendToEditText = (EditText)findViewById(R.id.send_to_emails);
        subjectEditText = (EditText)findViewById(R.id.email_subject);
        messageBodyEditText = (EditText)findViewById(R.id.email_content);
        
        sendButton = (Button)findViewById(R.id.send_email_button);
        sendButton.setOnClickListener(this);
        toButton = (Button)findViewById(R.id.send_to_button);
        toButton.setOnClickListener(this);
    }
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.send_to_button:
			openContactDialog();
		case R.id.send_email_button:
			sendEmail();
		}
		
	}
	
	private void sendEmail(){
		String emailSubject = subjectEditText.getText().toString();
		String emailMessage = messageBodyEditText.getText().toString();
		
		//make call to send email
	}
	
	private void openContactDialog(){
		ContactDialog contactDialog = new ContactDialog();
		contactDialog.setToken(token);
		contactDialog.show(getSupportFragmentManager(), "Contacts");
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.nav_menu, menu);
	    return true;
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.progress) {
			//go to progress page
			startProgressActivity();
			return true;
		} else if (item.getItemId() == R.id.email) {
			//do nothing
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
	
	private void startProgressActivity(){
		Intent intent = new Intent(this, Progress.class);
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
	
	private void startLoginActivity(){
		Intent intent = new Intent(this, PCLoginActivity.class);		
    	startActivity(intent);
	}

	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		ContactDialog contactDialog = (ContactDialog) dialog;
		ArrayList<Contact> selectedContacts = contactDialog.getSelectedContacts();
		String emails = sendToEditText.getText().toString();
		for(int i = 0; i < selectedContacts.size(); i++){
			if (emails.length() > 0){
				emails += ",";
			}
			emails += selectedContacts.get(i).getEmail();
		}
		
		sendToEditText.setText(emails);				
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		
	}

}
