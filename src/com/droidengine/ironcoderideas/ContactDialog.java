package com.droidengine.ironcoderideas;

import java.util.ArrayList;

import com.droidengine.ironcoderideas.API.GetAddressBookContacts;
import com.droidengine.ironcoderideas.API.GetAddressBookContacts.Contact;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;


public class ContactDialog extends DialogFragment{
	private static final String TAG = "IRONCODER";
	
	private ArrayList<Contact> selectedContacts;
	private CharSequence[] contactsForDialogList;
	private ArrayList<Contact> contactList;
	
	private String token;
	
	public interface NoticeDialogListener {
		public void onDialogPositiveClick(DialogFragment dialog);
		public void onDialogNegativeClick(DialogFragment dialog);
	}
	
	NoticeDialogListener listener;
	
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState){
		selectedContacts = new ArrayList<Contact>();
		contactsForDialogList = getContacts();
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Contacts");
		
		builder.setMultiChoiceItems(contactsForDialogList, null, new DialogInterface.OnMultiChoiceClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				if (isChecked){
					selectedContacts.add(contactList.get(which));
				} else {
					selectedContacts.remove(which);
				}
				
			}
		});
		
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				listener.onDialogPositiveClick(ContactDialog.this);
			}
		});
		
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				listener.onDialogNegativeClick(ContactDialog.this);
			}
		});
					
		return builder.create();
	}
	
	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		
		try {
			listener = (NoticeDialogListener) activity;
			
		}catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement NoticeDialogListener");
		}
	}
	
	public void setToken(String auth){
		token = auth;
	}
	
	private CharSequence[] getContacts(){
		GetAddressBookContacts contactAPI = new GetAddressBookContacts(token); 
		
		try{
			contactList = contactAPI.getContacts();
		}catch(Exception e){
			Log.d(TAG, e.toString());
		}
		
		CharSequence[] list = new CharSequence[contactList.size()];
		
		for(int i = 0; i < list.length; i++){
			list[i] = contactList.get(i).getName();
		}
				
		return list;
	}
	
	ArrayList<Contact> getSelectedContacts(){
		return selectedContacts;
	}
}
