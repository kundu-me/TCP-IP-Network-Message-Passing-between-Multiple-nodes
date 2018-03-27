package org.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.exception.ConfigException;
import org.exception.NodeConfigException;
import org.exception.ParsingException;
import org.exception.PropertyException;
import org.util.ConfigParserUtil;

/**
 * 
 * @author	Nirmallya Kundu
 * @id	 	nxk161830 
 * @email 	nxk161830@utdallas.edu
 *
 */
public class ConfigBO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static ConfigBO							objConfigBO;

	private int 									intNumberOfNodes;

	private String 									strConfigFileName;
	private String 									strConfigFilePath;

	private Map<Integer, NodeBO>					mapNodeBO;

	private Map<Integer, ArrayList<Integer>> 		mapNodePath;
	
	private Map<Integer, List<TokenBO>>				lstNodeTokenBO;

	/**
	 * Singleton Constructor to make
	 * a single object of the ConfigBO Business Object
	 */
	private ConfigBO() {

		super();
		initializeNodeAttributes(0, 0);
	}

	/**
	 *  static getSingletonInstance() Method is used to
	 *  call the private constructor to create the object 
	 *  
	 * @param intNumberOfNodes
	 * @param intNumberOfNodeAttributes
	 * @return
	 */
	public static ConfigBO getSingletonInstance() {

		if(null == objConfigBO) {

			try {
				
				objConfigBO			= new ConfigBO();
				
				objConfigBO			= ConfigParserUtil.parse(objConfigBO);
			} 
			catch (PropertyException e) {
			
				e.printStackTrace();
			} 
			catch (ConfigException e) {

				e.printStackTrace();
			} 
			catch (ParsingException e) {

				e.printStackTrace();
			} catch (NodeConfigException e) {

				e.printStackTrace();
			}
		}

		return objConfigBO;
	}

	/**
	 * Used to initialize the variables with
	 * the default values
	 * 
	 * @param intNumberOfNodes
	 * @param intNumberOfNodeAttributes
	 */
	public void initializeNodeAttributes(int intNumberOfNodes, int intNumberOfNodeAttributes) {

		this.mapNodeBO					= new HashMap<Integer, NodeBO>();
		this.mapNodePath				= new HashMap<Integer, ArrayList<Integer>>();
	}

	/**
	 * Reading the config file and
	 * creating each node with their 
	 * node id, hostName, port number, random label value
	 * 
	 * @param intRowIndex
	 * @param strArrNodeLocationData
	 * @param intNodeLocationDataCount
	 * @throws NumberFormatException
	 * @throws NodeConfigException
	 */
	public void addNodeLocation(int intRowIndex, String[] strArrNodeLocationData, 
			int intNodeLocationDataCount) throws NumberFormatException, NodeConfigException {

		NodeBO objNodeBO				= new NodeBO();
		objNodeBO.setIntNodeLabelValue(-1);

		for(int index = 0; index < intNodeLocationDataCount; index++) {

			String strNodeValue			= (null != strArrNodeLocationData[index])? (strArrNodeLocationData[index]).trim() : "";

			try {

				switch(index) {

				case 0: 
					objNodeBO.setIntNodeIdentifier(Integer.parseInt(strNodeValue));
					break;

				case 1:
					objNodeBO.setStrHostName(strNodeValue);
					break;

				case 2:
					objNodeBO.setIntPort(Integer.parseInt(strNodeValue));
					break;
				}
			}
			catch(NumberFormatException e) {

				String strLoggerMessage				= "NumberFormatException : " + e.getMessage();
				System.out.println(strLoggerMessage);
				e.printStackTrace();
				throw new NodeConfigException(strLoggerMessage);
			}
		}

		mapNodeBO.put(objNodeBO.getIntNodeIdentifier(), objNodeBO);
	}

	/**
	 * Adding the node path for each node
	 * 
	 * @param intRowCurrentNodeIndex
	 * @param strArrCurrentNodePathData
	 * @param intCountNodePathDataCount
	 * @throws NumberFormatException
	 */
	public void addNodePath(int intRowCurrentNodeIndex, String[] strArrCurrentNodePathData, 
			int intCountNodePathDataCount) throws NumberFormatException {

		for(int index = 0; index <= (intCountNodePathDataCount); index++) {

			String strPathValue										= null;

			if(index < intCountNodePathDataCount) {

				strPathValue										= strArrCurrentNodePathData[index];
			}
			else {

				strPathValue										= strArrCurrentNodePathData[0];
			}

			strPathValue											= (null != strPathValue)? strPathValue.trim() : "0";
			int intPathValue										= 0;
			try {

				intPathValue										= Integer.parseInt(strPathValue);
			}
			catch(NumberFormatException e) {

				String strLoggerMessage								= "NumberFormatException : " + e.getMessage();
				System.out.println(strLoggerMessage);
				e.printStackTrace();
				throw new NumberFormatException(strLoggerMessage);
			}

			ArrayList<Integer> lstNodePath							= mapNodePath.get(intRowCurrentNodeIndex);
			lstNodePath												= (null == lstNodePath)? new ArrayList<Integer>() : lstNodePath;
			lstNodePath.add(intPathValue);
			mapNodePath.put(intRowCurrentNodeIndex, lstNodePath);
		}

	}

	/**
	 * For Printing The Configuration File 
	 */
	@Override
	public String toString() {
		return "ConfigBO [intNumberOfNodes=" + intNumberOfNodes
				+ ", strConfigFileName=" + strConfigFileName
				+ ", strConfigFilePath=" + strConfigFilePath
				+ ", mapNodeBO=" + mapNodeBO 
				+ ", mapNodePath=" + mapNodePath + "]";
	}

	/**
	 *  Getters And Setters
	 */
	public int getIntNumberOfNodes() {
		return intNumberOfNodes;
	}

	public void setIntNumberOfNodes(int intNumberOfNodes) {
		this.intNumberOfNodes = intNumberOfNodes;
	}

	public String getStrConfigFileName() {
		return strConfigFileName;
	}

	public void setStrConfigFileName(String strConfigFileName) {
		this.strConfigFileName = strConfigFileName;
	}

	public String getStrConfigFilePath() {
		return strConfigFilePath;
	}

	public void setStrConfigFilePath(String strConfigFilePath) {
		this.strConfigFilePath = strConfigFilePath;
	}

	public Map<Integer, NodeBO> getMapNodeBO() {
		return mapNodeBO;
	}

	public void setMapNodeBO(Map<Integer, NodeBO> mapNodeBO) {
		this.mapNodeBO = mapNodeBO;
	}

	public Map<Integer, ArrayList<Integer>> getMapNodePath() {
		return mapNodePath;
	}

	public void setMapNodePath(Map<Integer, ArrayList<Integer>> mapNodePath) {
		this.mapNodePath = mapNodePath;
	}

	public Map<Integer, List<TokenBO>> getLstNodeTokenBO() {
		return lstNodeTokenBO;
	}

	public void setLstNodeTokenBO(Map<Integer, List<TokenBO>> lstNodeTokenBO) {
		this.lstNodeTokenBO = lstNodeTokenBO;
	}

}
