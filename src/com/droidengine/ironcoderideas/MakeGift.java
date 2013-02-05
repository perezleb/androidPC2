package com.droidengine.ironcoderideas;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.droidengine.ironcoderideas.API.MakeGiftAPI;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MakeGift extends Activity implements OnClickListener {

	private static final String TAG = "IRONCODER";

	private static final String TOKEN_KEY = "TOKEN";
	private static final String CONS_ID_KEY = "CONS_ID";
	private static final String FR_ID_KEY = "FR_ID";
	private static final String LOGIN_ERROR_KEY = "ERROR";

	private String token;
	private String consID;
	private String frID;

	public Button submitGiftButton;

	private TextView giftFirstName;
	private TextView giftLastName;
	private TextView giftEmail;
	private TextView giftAmount;

	private RadioButton cash;
	private RadioButton credit;

	private TextView billingCardNumber;
	private TextView billingCardCVV;
	private TextView cardExpirationMonth;
	private TextView cardExpirationYear;

	private TextView billingFirstName;
	private TextView billingLastName;
	private TextView billingStreet;
	private TextView billingCity;
	private TextView billingState;
	private TextView billingZip;
	
	private Button emailNavButton;
	private Button progressNavButton;

	private RelativeLayout cardBillingInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		token = getIntent().getStringExtra(TOKEN_KEY);
		consID = getIntent().getStringExtra(CONS_ID_KEY);
		frID = getIntent().getStringExtra(FR_ID_KEY);
		
		if (token == null || consID == null || frID == null){
			Log.d(TAG, "token or cons_id is null");
        	// Kick back to login screen
        	
        	Intent intent = new Intent(this, PCLoginActivity.class);
        	intent.putExtra(LOGIN_ERROR_KEY, "ERROR");
        	
    		startActivity(intent);
        	return;
		}
		
		setContentView(R.layout.make_gift);

		cardBillingInfo = (RelativeLayout) findViewById(R.id.cardBillingInfo);
		cardBillingInfo.setVisibility(View.GONE);		

		giftFirstName = (TextView) findViewById(R.id.giftFirstName);
		giftLastName = (TextView) findViewById(R.id.giftLastName);
		giftEmail = (TextView) findViewById(R.id.giftEmail);
		giftAmount = (TextView) findViewById(R.id.giftAmount);

		credit = (RadioButton) findViewById(R.id.credit);
		cash = (RadioButton) findViewById(R.id.cash);

		billingCardNumber = (TextView) findViewById(R.id.billingCardNumber);
		billingCardCVV = (TextView) findViewById(R.id.billingCardCVV);
		cardExpirationMonth = (TextView) findViewById(R.id.billingExpirationMonth);
		cardExpirationYear = (TextView) findViewById(R.id.billingExpirationYear);

		billingFirstName = (TextView) findViewById(R.id.billingFirstName);
		billingLastName = (TextView) findViewById(R.id.billingLastName);
		billingStreet = (TextView) findViewById(R.id.billingStreet1);
		billingCity = (TextView) findViewById(R.id.billingCity);
		billingState = (TextView) findViewById(R.id.billingState);
		billingZip = (TextView) findViewById(R.id.billing_zip);

		submitGiftButton = (Button) findViewById(R.id.submitGift);
		submitGiftButton.setOnClickListener(this);
		
		emailNavButton = (Button) findViewById(R.id.email_nav_button);
		emailNavButton.setOnClickListener(this);
		progressNavButton = (Button) findViewById(R.id.progress_nav_button);
		progressNavButton.setOnClickListener(this);

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
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.logout) {
			// go to progress page
			startLoginActivity();
			return true;		
		} else if (item.getItemId() == R.id.my_teamraisers) {
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

	private void startEmailActivity() {
		Intent intent = new Intent(this, Email.class);
		intent.putExtra(CONS_ID_KEY, consID);
		intent.putExtra(TOKEN_KEY, token);
		intent.putExtra(FR_ID_KEY, frID);
		startActivity(intent);
	}

	private void startProgressActivity() {
		Intent intent = new Intent(this, Progress.class);
		intent.putExtra(CONS_ID_KEY, consID);
		intent.putExtra(TOKEN_KEY, token);
		intent.putExtra(FR_ID_KEY, frID);
		startActivity(intent);
	}

	private void startRegisteredTeamraisersActivity() {
		Intent intent = new Intent(this, RegisteredTeamraisers.class);
		intent.putExtra(CONS_ID_KEY, consID);
		intent.putExtra(TOKEN_KEY, token);
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.submitGift) {

			Double amount = Double.parseDouble(giftAmount.getText().toString());
			NumberFormat fmt = NumberFormat.getCurrencyInstance(new Locale(
					"en", "US"));

			AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
			alt_bld.setMessage("Process gift for " + fmt.format(amount) + " ?")
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
		else if (v.getId() == R.id.email_nav_button){
			startEmailActivity();
		}
		else if (v.getId() == R.id.progress_nav_button){
			startProgressActivity();
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

	private Map<String, String> buildParamMap() {
		Map<String, String> params = new HashMap<String, String>();

		params.put("fr_id", frID);
		params.put("sso_auth_token", token);
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
