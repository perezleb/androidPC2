package com.droidengine.ironcoderideas.API;

import java.util.HashMap;

import org.w3c.dom.Element;

public class SendEmailAPI extends APIResponseManager{
	
	private static String TAG = "IRONCODER";
	private static String METHOD = "sendTafMessage";
	private static String API = "CRTeamraiserAPI?";
	
	private final String layoutID = "1";
	private final String messageID = "-1";
	
	private String token;
	private String frID;
	private String subject;
	private String message;
	private String recipients;
	private String messageName;
	
	private String emailMessageStatus;
	
	public SendEmailAPI(String auth, String id, String messageSubject, String messageBody, String toList){
		token = auth;
		frID = id;
		subject = messageSubject;
		message = messageBody;
		recipients = toList;
		messageName = subject;
	}
	
	public void sendEmail() throws Exception{
		
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("sso_auth_token", token);
		params.put("fr_id", frID);
		params.put("message_id", messageID);
		params.put("layout_id", layoutID);
		params.put("message_name", messageName);
		params.put("recipients", recipients);
		params.put("subject", subject);
		params.put("message_body", message);
				
		
		APIController controller = new APIController(API, METHOD, params);
		controller.execute();
		doc = controller.get();
		
		if (isErrorMessage()){
			if (errorMessage != null)
				throw new Exception(errorMessage);
			else
				throw new Exception("Technical Difficulties");
		}
		
		
		Element emailResponse = (Element) doc.getElementsByTagName("sendTafMessageResponse").item(0);		
		emailMessageStatus = getElementValue(emailResponse.getElementsByTagName("success"));
	}
	
	public String messageSuccess(){
		return emailMessageStatus;
	}
	

}
