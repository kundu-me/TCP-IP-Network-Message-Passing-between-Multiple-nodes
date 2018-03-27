package org.exception;
/**
 * 
 * @author Nirmallya
 *
 */
public class ParsingException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String strMessage			= "";
	
	public ParsingException() {
		super();
	}

	public ParsingException(String message, Throwable cause) {
		
		super(message, cause);
		strMessage						= message;
	}

	public ParsingException(String message) {
		
		super(message);
		strMessage						= message;
	}

	public ParsingException(Throwable cause) {
		
		super(cause);
	}
	
	//Get The Exception Message
	public String getMessage() {
		
		return strMessage;
	}

}
