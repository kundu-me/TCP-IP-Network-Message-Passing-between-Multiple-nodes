package org.exception;
/**
 * 
 * @author Nirmallya
 *
 */
public class FileWriteException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String strMessage			= "";
	
	public FileWriteException() {
		super();
	}

	public FileWriteException(String message, Throwable cause) {
		
		super(message, cause);
		strMessage						= message;
	}

	public FileWriteException(String message) {
		
		super(message);
		strMessage						= message;
	}

	public FileWriteException(Throwable cause) {
		
		super(cause);
	}
	
	//Get The Exception Message
	public String getMessage() {
		
		return strMessage;
	}

}
