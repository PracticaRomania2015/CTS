package com.cts.communication;

import com.cts.utils.ConfigReader;

public enum ResponseValues {
	
	SUCCESS("success", ConfigReader.getInstance().getValueForKey("success")),
	EMPTYTITLE("error", ConfigReader.getInstance().getValueForKey("emptyTitle")),
	EMPTYFIRSTNAME("error", ConfigReader.getInstance().getValueForKey("emptyFirstName")),
	EMPTYLASTNAME("error", ConfigReader.getInstance().getValueForKey("emptyLastName")),
	EMPTYEMAIL("error", ConfigReader.getInstance().getValueForKey("emptyEmail")),
	EMPTYPASSWORD("error", ConfigReader.getInstance().getValueForKey("emptyPassword")),
	INVALIDEMAILFORMAT("error", ConfigReader.getInstance().getValueForKey("invalidEmailFormat")),
	DBERROR("error", ConfigReader.getInstance().getValueForKey("dbError")),
	UNKNOWN("error", ConfigReader.getInstance().getValueForKey("unknownError")),
	QUESTIONSERROR("error", ConfigReader.getInstance().getValueForKey("questionsError")),
	QUESTIONSANSWERSERROR("error", ConfigReader.getInstance().getValueForKey("questionsAnswersError")),
	
	LOGININVALIDCREDENTIALS("error", ConfigReader.getInstance().getValueForKey("loginInvalidCredentials")),
	
	REGISTERSUCCESS("success", ConfigReader.getInstance().getValueForKey("registerSuccess")),
	REGISTEREXISTINGEMAIL("error", ConfigReader.getInstance().getValueForKey("registerExistingEmail")),
	
	RECOVERYSUCCESS("success", ConfigReader.getInstance().getValueForKey("recoverySuccess")),
	RECOVERYINCORRECTEMAIL("error", ConfigReader.getInstance().getValueForKey("recoveryIncorrectEmail")),
	
	TICKETEMPTYSUBJECT("error", ConfigReader.getInstance().getValueForKey("ticketEmptySubject")),
	TICKETEMPTYCATEGORY("error", ConfigReader.getInstance().getValueForKey("ticketEmptyCategory")),
	TICKETEMPTYDESCRIPTION("error", ConfigReader.getInstance().getValueForKey("ticketEmptyDescription")),
	TICKETEMPTYCOMMENT("error", ConfigReader.getInstance().getValueForKey("ticketEmptyComment")),
	
	UPDATEUSERSUCCESS("success", ConfigReader.getInstance().getValueForKey("updateUserSuccess")),
	UPDATEUSEREMPTYOLDPASSWORD("error", ConfigReader.getInstance().getValueForKey("updateUserEmptyOldPassword")),
	UPDATEUSEREMPTYNEWPASSWORD("error", ConfigReader.getInstance().getValueForKey("updateUserEmptyNewPassword")),
	UPDATEUSERINCORRECTOLDPASSWORD("error", ConfigReader.getInstance().getValueForKey("updateUserInvalidOldPassword")),
	
	CATEGORYEMPTYID("error", ConfigReader.getInstance().getValueForKey("categoryEmptyId")),
	CATEGORYEMPTYNAME("error", ConfigReader.getInstance().getValueForKey("categoryEmptyName")),
	
	UPDATEUSERNOTIFICATIONSSETTINGSSUCCESS("success", ConfigReader.getInstance().getValueForKey("updateUserNotificationsSuccess")),
	
	ROOTMANAGEMENTEMPTYSYSADMIN("error", ConfigReader.getInstance().getValueForKey("rootManagementEmptySysAdmin"));
	
	private String type;
	private String description;
	
	ResponseValues(String type, String description) {
		this.type = type;
		this.description = description;
	}
	
	public String getType() {
		return type;
	}
	
	public String getDescription() {
		return description;
	}
}