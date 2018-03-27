package org.exception;
/**
 * 
 * @author Nirmallya
 *
 */
public class ConfigException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String strMessage			= "";
	
	public ConfigException() {
		super();
	}

	public ConfigException(String message, Throwable cause) {
		
		super(message, cause);
		strMessage						= message;
	}

	public ConfigException(String message) {
		
		super(message);
		strMessage						= message;
	}

	public ConfigException(Throwable cause) {
		
		super(cause);
	}
	
	//Get The Exception Message
	public String getMessage() {
		
		return strMessage;
	}

}
