package com.droidengine.ironcoderideas;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.droidengine.ironcoderideas.API.MakeGiftAPI;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MakeGift extends AbstractPCActivity implements OnClickListener {

	public Button submitGiftButton;

	private EditText giftFirstName;
	private EditText giftLastName;
	private EditText giftEmail;
	private EditText giftAmount;

	private RadioButton cash;
	private RadioButton credit;

	private EditText billingCardNumber;
	private EditText billingCardCVV;
	private EditText cardExpirationMonth;
	private EditText cardExpirationYear;

	private EditText billingFirstName;
	private EditText billingLastName;
	private EditText billingStreet;
	private EditText billingCity;
	private EditText billingState;
	private EditText billingZip;

	private RelativeLayout cardBillingInfo;

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
		
		setContentView(R.layout.make_gift);
		
		buildActionBar();
		
		cardBillingInfo = (RelativeLayout) findViewById(R.id.cardBillingInfo);
		cardBillingInfo.setVisibility(View.GONE);		

		giftFirstName = (EditText) findViewById(R.id.giftFirstName);
		giftLastName = (EditText) findViewById(R.id.giftLastName);
		giftEmail = (EditText) findViewById(R.id.giftEmail);
		giftAmount = (EditText) findViewById(R.id.giftAmount);

		credit = (RadioButton) findViewById(R.id.credit);
		cash = (RadioButton) findViewById(R.id.cash);

		billingCardNumber = (EditText) findViewById(R.id.billingCardNumber);
		billingCardCVV = (EditText) findViewById(R.id.billingCardCVV);
		cardExpirationMonth = (EditText) findViewById(R.id.billingExpirationMonth);
		cardExpirationYear = (EditText) findViewById(R.id.billingExpirationYear);

		billingFirstName = (EditText) findViewById(R.id.billingFirstName);
		billingLastName = (EditText) findViewById(R.id.billingLastName);
		billingStreet = (EditText) findViewById(R.id.billingStreet1);
		billingCity = (EditText) findViewById(R.id.billingCity);
		billingState = (EditText) findViewById(R.id.billingState);
		billingZip = (EditText) findViewById(R.id.billing_zip);

		submitGiftButton = (Button) findViewById(R.id.submitGift);
		submitGiftButton.setOnClickListener(this);
		
		
		
	
		
		giftAmount.addTextChangedListener(new TextWatcher(){
			private String current = "";

	        DecimalFormat dec = new DecimalFormat("0.00");
	        @Override
	        public void afterTextChanged(Editable arg0) {}
	       
	        @Override
	        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
	       
	        @Override
	        public void onTextChanged(CharSequence s, int start, int before, int count) {	           
	            if(!s.toString().equals(current)){
	            	giftAmount.removeTextChangedListener(this);

	                String cleanString = s.toString().replaceAll("[$,.]", "");

	                double parsed = Double.parseDouble(cleanString);
	                String formated = NumberFormat.getCurrencyInstance().format((parsed/100));

	                current = formated;
	                giftAmount.setText(formated);
	                giftAmount.setSelection(formated.length());

	                giftAmount.addTextChangedListener(this);
	             }}
		});
	}


	public void onPaymentTypeRadioClicked(View view) {
		boolean checked = ((RadioButton) view).isChecked();
		switch (view.getId()) {
		case R.id.cash:
			if (checked)
				cardBillingInfo.setVisibility(View.GONE);
			break;
		case R.id.credit:
			if (checked)
				cardBillingInfo.setVisibility(View.VISIBLE);
			break;
		}
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.nav_menu, menu);
	    menu.removeItem(R.id.make_gift);
		return true;
	}
	
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.submitGift) {
			


			AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
			alt_bld.setMessage("Process gift for " + giftAmount.getText().toString() + " ?")
					.setCancelable(true)
					.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									processGift();
									dialog.dismiss();

								}
							})
					.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.dismiss();

								}
							});

			AlertDialog alert = alt_bld.create();
			alert.show();
		}
	}

	private void processGift() {
		Log.d(TAG, "Attempting to process gift");
		Map<String, String> parameters = buildParamMap();
		MakeGiftAPI makeGiftmanager = new MakeGiftAPI();
		AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
		String message = "Gift recorded successfully!";

		try {
			makeGiftmanager.processGift(parameters);
		} catch (Exception e) {
			message = "Unable to process gift";
			Log.d(TAG, e.toString());
		}

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
	
	@Override
	public void finish(){
		super.finish();
	}

	private Map<String, String> buildParamMap()  {
		Map<String, String> params = new HashMap<String, String>();

		params.put("fr_id", frID);
		params.put("sso_auth_token", token);
		
		Number number = null;
		try {
			number = NumberFormat.getCurrencyInstance(new Locale("en", "US")).parse(giftAmount.getText().toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		params.put("gift_amount", String.valueOf(number.doubleValue()));
		
		addElement(params, "first_name", giftFirstName);
		addElement(params, "gift_amount", giftAmount);
		addElement(params, "last_name", giftLastName);

		RadioGroup paymentSelectionType = (RadioGroup) findViewById(R.id.paymentMethod);
		if (paymentSelectionType.getCheckedRadioButtonId() == cash.getId()) {
			params.put("payment_type", "cash");
		} else {
			params.put("payment_type", "credit");

			addElement(params, "credit_card_number", billingCardNumber);
			addElement(params, "credit_card_verification_code", billingCardCVV);
			addElement(params, "credit_card_month", cardExpirationMonth);
			addElement(params, "credit_card_year", cardExpirationYear);

			addElement(params, "billing_first_name", billingFirstName);
			addElement(params, "billing_last_name", billingLastName);
			addElement(params, "billing_street1", billingStreet);
			addElement(params, "billing_city", billingCity);
			addElement(params, "billing_state", billingState);
			addElement(params, "billing_zip", billingZip);
		}

		return params;
	}

	private void addElement(Map<String, String> params, String key,
			TextView text) {
		String value = text.getText().toString();
		if (!value.trim().equals("")) {
			params.put(key, value);
		}

	}

}
