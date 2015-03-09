package pl.library.model; 

/* 
 * SecurityModel Class
 * Check if values send by user
 * are correct (allowed)
 */
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
	/*
	 * Check if the password have at least one digit, one upper case letter, 
	 * one lower case letter and have at least eight places
	 * 
	 * @param str the string with password
	 * @return boolean true if password meet the requirements
	 */
	public boolean checkPassword(String str) {
		if(str != null && str.trim().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$")) {
			return true;
		}
		return false;
	}
	
	/*
	 * Check if string are not null and not empty
	 * 
	 * @param str the string to check
	 * @return boolean true if not null & not empty
	 */
	public boolean checkString(String str) {
		if(str != null && str.trim().length() > 0) {
			return true;
		}
		return false;
	}
	
	/*
	 * Check if input String contains only numbers
	 * 
	 * @param String id	   expected string with number
	 * @return boolean 	   true if string contains only numbers
	 */
	public boolean checkIsInteger(String id) {
		if(id != null && id.trim().length() > 0 && id.matches("[0-9]+")) 
			return true;
		return false;
	}
	
	/*
	 * Check if input String contains only double type
	 * 
	 * @param String id	   expected string with double
	 * @return boolean 	   true if string contains only numbers
	 */
	public boolean checkIsNumber(String id) {
		if(id != null && id.trim().length() > 0 && id.matches("[0-9]+.[0-9]+")) 
			return true;
		return false;
	}
	
	
}