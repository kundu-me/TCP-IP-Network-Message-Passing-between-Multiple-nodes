package org.bo;

import java.io.Serializable;

import org.exception.NodeConfigException;

/**
 * 
 * @author	Nirmallya Kundu
 * @id	 	nxk161830 
 * @email 	nxk161830@utdallas.edu
 *
 */
public class NodeBO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private int 				intNodeIdentifier;
	private int 				intPort;
	private int					intNodeLabelValue;
	private String 				strHostName;

	/**
	 * 
	 * @throws NodeConfigException
	 */
	public NodeBO() throws NodeConfigException {
		
		super();
	}

	/**
	 * 
	 * @param intNodeIdentifier
	 * @param strHostName
	 * @param intPort
	 * @throws NodeConfigException
	 */
	public NodeBO(int intNodeIdentifier, String strHostName, int intPort) throws NodeConfigException {
		
		super();
		
		this.intNodeIdentifier 				= intNodeIdentifier;
		this.strHostName 					= strHostName;
		this.intPort			 			= intPort;
			
	}
	
	/**
	 * 
	 * @throws NodeConfigException
	 */

	/**
	 * For Printing The Configuration File 
	 */
	@Override
	public String toString() {
		return "NodeBO [intNodeIdentifier=" + intNodeIdentifier + ", intPort="
				+ intPort + ", intNodeLabelValue=" + intNodeLabelValue
				+ ", strHostName=" + strHostName + "]";
	}

	/**
	 * Getters And Setters
	 * 
	 */
	public int getIntNodeIdentifier() {
		return intNodeIdentifier;
	}

	public void setIntNodeIdentifier(int intNodeIdentifier) {
		this.intNodeIdentifier = intNodeIdentifier;
	}

	public int getIntPort() {
		return intPort;
	}

	public void setIntPort(int intPort) {
		this.intPort = intPort;
	}

	/*public int getIntNodeLabelValue() {
		return intNodeLabelValue;
	}*/

	public void setIntNodeLabelValue(int intNodeLabelValue) {
		this.intNodeLabelValue = intNodeLabelValue;
	}

	public String getStrHostName() {
		return strHostName;
	}

	public void setStrHostName(String strHostName) {
		this.strHostName = strHostName;
	}

}
