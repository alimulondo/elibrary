package pl.library.model; 

public class SecurityModel {
	
	/*
	^                 # start-of-string
	(?=.*[0-9])       # a digit must occur at least once
	(?=.*[a-z])       # a lower case letter must occur at least once
	(?=.*[A-Z])       # an upper case letter must occur at least once
	(?=.*[@#$%^&+=])  # a special character must occur at least once
	(?=\S+$)          # no whitespace allowed in the entire string
	.{8,}             # anything, at least eight places though
	$                 # end-of-string
	 */
	public boolean checkPassword(String str) {
		if(str != null && str.trim().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$")) {
			return true;
		}
		return false;
	}
	
	public boolean checkString(String str) {
		if(str != null && str.trim().length() > 0) {
			return true;
		}
		return false;
	}
}