package com.droidengine.ironcoderideas.API;

import java.util.Map;
import android.util.Log;

public class MakeGiftAPI extends APIResponseManager {
	private static String TAG = "IRONCODER";
	private static String METHOD = "addGift";
	private static String API = "CRTeamraiserAPI?";

	public void MakeGiftApi() {

	}

	public void processGift(Map<String, String> params) throws Exception {
		APIController controller = new SecureAPIController(API, METHOD, params);
		controller.execute();
		doc = controller.get();
		if (isErrorMessage()) {
			Log.d(TAG, "Error Message in Response");
			throw new Exception(errorMessage);
		}

	}
}
