package org.exception;
/**
 * 
 * @author Nirmallya
 *
 */
public class PropertyException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String strMessage			= "";
	
	public PropertyException() {
		super();
	}

	public PropertyException(String message, Throwable cause) {
		
		super(message, cause);
		strMessage						= message;
	}

	public PropertyException(String message) {
		
		super(message);
		strMessage						= message;
	}

	public PropertyException(Throwable cause) {
		
		super(cause);
	}
	
	//Get The Exception Message
	public String getMessage() {
		
		return strMessage;
	}

}
