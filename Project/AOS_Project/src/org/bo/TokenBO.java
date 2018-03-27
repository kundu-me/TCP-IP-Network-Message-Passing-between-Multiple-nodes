package org.bo;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * 
 * @author	Nirmallya Kundu
 * @id	 	nxk161830 
 * @email 	nxk161830@utdallas.edu
 *
 */
public class TokenBO implements Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String				strTokenId;
	private int					intSumLabelValues;
	private List<Integer>		lstOriginalNodePath;
	private int					intNodeIdentifier;
	
	private boolean				blnFlagTerminationToken;
	
	/**
	 * 
	 */
	public TokenBO() {
		
		super();
		defaultAssignment();
	}

	/**
	 * 
	 * @param objNodeBO
	 */
	public TokenBO(int intNodeIdentifier, int intNodeLabelValue) {

		defaultAssignment();
		this.intNodeIdentifier			= intNodeIdentifier;
		
		this.intSumLabelValues			= 0;
		this.lstOriginalNodePath		= ConfigBO.getSingletonInstance().getMapNodePath().get(intNodeIdentifier);
		
	}
	
	/**
	 * 
	 */
	private void defaultAssignment() {

		strTokenId				= UUID.randomUUID().toString();
		intSumLabelValues		= 0;
		setBlnFlagTerminationToken(false);
	}

	/**
	 * 
	 * @param intLabelValue
	 */
	public void updateIntSumLabelValues(int intLabelValue) {

		intSumLabelValues		+= intLabelValue;
	}
	
	/**
	 * 
	 * @param objNodeBO
	 */
	public void removeProcessedNodeIdentifier() {
		
		lstOriginalNodePath.remove(0);
	}

	/**
	 * 
	 */

	@Override
	public String toString() {
		return "TokenBO [intSumLabelValues=" + intSumLabelValues + ", lstOriginalNodePath="	+ lstOriginalNodePath + ", intNodeIdentifier=" + intNodeIdentifier + "]";
	}

	/**
	 * Getters And Setters
	 */
	public String getStrTokenId() {
		return strTokenId;
	}

	public void setStrTokenId(String strTokenId) {
		this.strTokenId = strTokenId;
	}

	public int getIntSumLabelValues() {
		return intSumLabelValues;
	}

	public void setIntSumLabelValues(int intSumLabelValues) {
		this.intSumLabelValues = intSumLabelValues;
	}

	public int getIntNodeIdentifier() {
		return intNodeIdentifier;
	}

	public void setIntNodeIdentifier(int intNodeIdentifier) {
		this.intNodeIdentifier = intNodeIdentifier;
	}

	public List<Integer> getLstOriginalNodePath() {
		return lstOriginalNodePath;
	}

	public void setLstOriginalNodePath(List<Integer> lstOriginalNodePath) {
		this.lstOriginalNodePath = lstOriginalNodePath;
	}

	public boolean isBlnFlagTerminationToken() {
		return blnFlagTerminationToken;
	}

	public void setBlnFlagTerminationToken(boolean blnFlagTerminationToken) {
		this.blnFlagTerminationToken = blnFlagTerminationToken;
	}
}
