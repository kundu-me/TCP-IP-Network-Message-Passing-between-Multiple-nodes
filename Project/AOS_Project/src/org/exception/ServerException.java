package org.exception;
/**
 * 
 * @author Nirmallya
 *
 */
public class ServerException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String strMessage			= "";
	
	public ServerException() {
		super();
	}

	public ServerException(String message, Throwable cause) {
		
		super(message, cause);
		strMessage						= message;
	}

	public ServerException(String message) {
		
		super(message);
		strMessage						= message;
	}

	public ServerException(Throwable cause) {
		
		super(cause);
	}
	
	//Get The Exception Message
	public String getMessage() {
		
		return strMessage;
	}

}
