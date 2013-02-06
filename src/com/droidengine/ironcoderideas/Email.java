package com.droidengine.ironcoderideas;

import java.util.ArrayList;

import com.droidengine.ironcoderideas.API.GetAddressBookContacts.Contact;
import com.droidengine.ironcoderideas.API.SendEmailAPI;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class Email extends AbstractPCActivity implements OnClickListener, ContactDialog.NoticeDialogListener{
	
	private Button toButton;
	private Button sendButton;
	private EditText sendToEditText;
	private EditText subjectEditText;
	private EditText messageBodyEditText;
	
	@SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        getConstituent(getIntent());
        
        if (token == null || consID == null || frID == null){
			Log.d(TAG, "token or cons_id is null");
        	// Kick back to login screen
        	
        	Intent intent = new Intent(this, PCLoginActivity.class);
        	intent.putExtra(LOGIN_ERROR_KEY, "ERROR");
        	
    		startActivity(intent);
        	return;
		}
        
        setContentView(R.layout.email);
        
        buildActionBar();
        
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
			break;
		case R.id.send_email_button:
			sendEmail();
			break;
		}
		
	}
	
	private void sendEmail(){
		String emailSubject = subjectEditText.getText().toString();
		String emailMessage = messageBodyEditText.getText().toString();
		String recipients = sendToEditText.getText().toString();
		
		SendEmailAPI emailAPI = new SendEmailAPI(token, frID, emailSubject, emailMessage, recipients);
		
		try {
			emailAPI.sendEmail();
		} catch(Exception e){
			Log.d(TAG, e.toString());
			showFailedDialog();
			return;
		}
						
		if (emailAPI.messageSuccess().equals("true")){
			showSuccessDialog();
		} else {
			showFailedDialog();
		}
	}
	
	private void showFailedDialog(){
		AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
		String message = "Email Failed";
		
		alt_bld.setMessage(message).setCancelable(false)
		.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				finish();
				onRestart();
			}
		});

		AlertDialog alert = alt_bld.create();
		alert.show();
	}
	
	private void showSuccessDialog(){
		AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
		String message = "Email Sent Successfully";
		
		alt_bld.setMessage(message).setCancelable(false)
		.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				finish();
				startProgressActivity();
			}
		});

		AlertDialog alert = alt_bld.create();
		alert.show();
	}
	
	private void openContactDialog(){
		ContactDialog contactDialog = new ContactDialog();
		contactDialog.setToken(token);
		contactDialog.show(getSupportFragmentManager(), "Contacts");
	}
	
	@SuppressLint("NewApi")
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.nav_menu, menu);
	    menu.removeItem(R.id.email);
	    return true;
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
