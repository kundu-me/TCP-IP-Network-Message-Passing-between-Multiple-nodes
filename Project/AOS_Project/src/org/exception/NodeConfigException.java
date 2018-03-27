package org.exception;
/**
 * 
 * @author Nirmallya
 *
 */
public class NodeConfigException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String strMessage			= "";
	
	public NodeConfigException() {
		super();
	}

	public NodeConfigException(String message, Throwable cause) {
		
		super(message, cause);
		strMessage						= message;
	}

	public NodeConfigException(String message) {
		
		super(message);
		strMessage						= message;
	}

	public NodeConfigException(Throwable cause) {
		
		super(cause);
	}
	
	//Get The Exception Message
	public String getMessage() {
		
		return strMessage;
	}

}
