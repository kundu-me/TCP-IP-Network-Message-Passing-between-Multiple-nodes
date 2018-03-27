package org.exception;
/**
 * 
 * @author Nirmallya
 *
 */
public class ConnectionException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String strMessage			= "";
	
	public ConnectionException() {
		super();
	}

	public ConnectionException(String message, Throwable cause) {
		
		super(message, cause);
		strMessage						= message;
	}

	public ConnectionException(String message) {
		
		super(message);
		strMessage						= message;
	}

	public ConnectionException(Throwable cause) {
		
		super(cause);
	}
	
	//Get The Exception Message
	public String getMessage() {
		
		return strMessage;
	}

}