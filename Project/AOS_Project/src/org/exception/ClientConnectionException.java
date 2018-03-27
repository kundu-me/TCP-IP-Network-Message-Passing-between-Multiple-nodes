package org.exception;
/**
 * 
 * @author Nirmallya
 *
 */
public class ClientConnectionException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String strMessage			= "";
	
	public ClientConnectionException() {
		super();
	}

	public ClientConnectionException(String message, Throwable cause) {
		
		super(message, cause);
		strMessage						= message;
	}

	public ClientConnectionException(String message) {
		
		super(message);
		strMessage						= message;
	}

	public ClientConnectionException(Throwable cause) {
		
		super(cause);
	}
	
	//Get The Exception Message
	public String getMessage() {
		
		return strMessage;
	}

}
