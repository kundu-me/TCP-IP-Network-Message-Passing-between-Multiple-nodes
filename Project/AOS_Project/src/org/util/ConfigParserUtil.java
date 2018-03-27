package org.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.bo.ConfigBO;
import org.bo.ProjectConstants;
import org.exception.ConfigException;
import org.exception.NodeConfigException;
import org.exception.ParsingException;
import org.exception.PropertyException;
/**
 * 
 * @author	Nirmallya Kundu
 * @id	 	nxk161830 
 * @email 	nxk161830@utdallas.edu
 *
 */
public class ConfigParserUtil {

	private static String strLoggerMessage			= "";
	
	public static ConfigBO parse(ConfigBO objConfigBO) throws PropertyException, ConfigException, ParsingException, NodeConfigException {
		
		String strConfigFile				= null;
		int intNodeAttributeColumnCount		= 3;
		
		int intFlagParseStep				= 1;
		
		FileReader objFileReader			= null;
		BufferedReader objBufferedReader	= null;
		
		try {
			
			String strConfigFileName		= ProjectUtil.getProperty(ProjectConstants.PROP_KEY_CONFIG_FILE_NAME);
			String strConfigFilePath		= System.getProperty("user.dir") + ProjectUtil.getProperty(ProjectConstants.PROP_KEY_CONFIG_FILE_PATH);
			strConfigFile					= strConfigFilePath + File.separator + strConfigFileName;
			
			objConfigBO.setStrConfigFileName(strConfigFileName);
			objConfigBO.setStrConfigFilePath(strConfigFilePath);
		}
		catch(PropertyException e) {
			
			strLoggerMessage				= "PropertyException : " + e.getMessage();
			System.out.println(strLoggerMessage);
			e.printStackTrace();
			throw new PropertyException(strLoggerMessage);
		}
		catch (Exception e) {

			strLoggerMessage				= "Exception : " + e.getMessage();
			System.out.println(strLoggerMessage);
			e.printStackTrace();
			throw new ConfigException(strLoggerMessage);
		}

		try {
			
			String strLine					= null;
			
			objFileReader					= new FileReader(strConfigFile);
			objBufferedReader				= new BufferedReader(objFileReader);
			
			while(null != (strLine = objBufferedReader.readLine())) {
			
				strLine						= (null != strLine)? strLine.trim() : strLine;
				if((strLine.length() == 0) || (strLine.startsWith("#"))) {
					
					continue;
				}
				
				if(intFlagParseStep == 1) {

					/**
					 * CASE 1 : Number of Nodes
					 */

					intFlagParseStep					= intFlagParseStep + 1;
					try {
							
						int intNumberOfNodes			= Integer.parseInt(strLine.trim());
						objConfigBO.setIntNumberOfNodes(intNumberOfNodes);
						objConfigBO.initializeNodeAttributes(intNumberOfNodes, intNodeAttributeColumnCount);
					}
					catch(NumberFormatException e) {
							
						strLoggerMessage				= "NumberFormatException : " + e.getMessage();
						System.out.println(strLoggerMessage);
						e.printStackTrace();
						throw new ParsingException(strLoggerMessage);
					}
					catch(Exception e) {
							
						strLoggerMessage				= "Exception : " + e.getMessage();
						System.out.println(strLoggerMessage);
						e.printStackTrace();
						throw new ConfigException(strLoggerMessage);
					}
				}
				else if(intFlagParseStep == 2) {
					
					/**
					 * CASE 2 : Location of each node
					 */

					intFlagParseStep					= intFlagParseStep + 1;
					
					for(int intNodeIndex = 0; intNodeIndex < objConfigBO.getIntNumberOfNodes(); intNodeIndex++) {
						
						if(intNodeIndex != 0) {
							
							strLine 					= objBufferedReader.readLine();
						}
						
						strLine							= (null != strLine)? strLine.trim() : strLine;
						String[] strArrNodeData			= strLine.split("\\s+");
								
						try {
							
							objConfigBO.addNodeLocation(intNodeIndex, strArrNodeData, intNodeAttributeColumnCount);
						}
						catch(NumberFormatException e) {
							
							strLoggerMessage			= "NumberFormatException : " + e.getMessage();
							System.out.println(strLoggerMessage);
							e.printStackTrace();
							throw new ParsingException(strLoggerMessage);
						}
						catch(NodeConfigException e) {
							
							strLoggerMessage			= "NodeConfigException : " + e.getMessage();
							System.out.println(strLoggerMessage);
							e.printStackTrace();
							throw new NodeConfigException(strLoggerMessage);
						}
						catch(Exception e) {
								
							strLoggerMessage			= "Exception : " + e.getMessage();
							System.out.println(strLoggerMessage);
							e.printStackTrace();
							throw new ConfigException(strLoggerMessage);
						}
					}
				}
				else if(intFlagParseStep == 3) {
									
					/**
					 * CASE 3 : Path of each node
					 */
	
					intFlagParseStep						= intFlagParseStep + 1;
					for(int intNodeIndex = 0; intNodeIndex < objConfigBO.getIntNumberOfNodes(); intNodeIndex++) {
								
						if(intNodeIndex != 0) {
						
							strLine 						= objBufferedReader.readLine();
						}
						
						strLine								= (null != strLine)? strLine.trim() : strLine;
						
						String[] strArrNodePathData 		= strLine.split("#.*$");
						strArrNodePathData[0] 				= (strArrNodePathData[0]).replace("(", "");
						strArrNodePathData[0] 				= (strArrNodePathData[0]).replace(")", "");
						strArrNodePathData[0] 				= (strArrNodePathData[0]).replace(",", " ");
						String[] strArrCurrentNodePathData 	= strArrNodePathData[0].split("\\s+");
						int intCountNodePathData			= (null != strArrCurrentNodePathData)?strArrCurrentNodePathData.length : 0;
								
						try {
							
							objConfigBO.addNodePath(intNodeIndex, strArrCurrentNodePathData, intCountNodePathData);
						}
						catch(NumberFormatException e) {
							
							strLoggerMessage				= "NumberFormatException : " + e.getMessage();
							System.out.println(strLoggerMessage);
							e.printStackTrace();
							throw new ParsingException(strLoggerMessage);
						}
						catch(Exception e) {
								
							strLoggerMessage				= "Exception : " + e.getMessage();
							System.out.println(strLoggerMessage);
							e.printStackTrace();
							throw new ConfigException(strLoggerMessage);
						}
						
					}
				}
			}
		}
		catch(FileNotFoundException e) {
			
			strLoggerMessage				= "FileNotFoundException : " + e.getMessage();
			System.out.println(strLoggerMessage);
			e.printStackTrace();
			throw new ParsingException(strLoggerMessage);
		}
		catch(Exception e) {
			
			strLoggerMessage				= "Exception : " + e.getMessage();
			System.out.println(strLoggerMessage);
			e.printStackTrace();
			throw new ConfigException(strLoggerMessage);
		}
		finally {
			
			if((null != objFileReader) || (null != objBufferedReader)) {
				
				try {
					
					objFileReader.close();
					objFileReader			= null;
					
					objBufferedReader.close();
					objBufferedReader		= null;
				}
				catch(IOException e) {
					
					strLoggerMessage				= "IOException : " + e.getMessage();
					System.out.println(strLoggerMessage);
					e.printStackTrace();
					throw new ParsingException(strLoggerMessage);
				}
				catch(Exception e) {
					
					strLoggerMessage				= "Exception : " + e.getMessage();
					System.out.println(strLoggerMessage);
					e.printStackTrace();
					throw new ConfigException(strLoggerMessage);
				}
				
			}
		}

		return objConfigBO;
	}
	
	/**
	 * 
	 * @param args
	 *//*
	public static void main(String[] args) {
		
		try {

			ConfigBO objConfigBO 							=	ConfigParserUtil.parse();
			
			int intNumberOfNodes							= objConfigBO.getIntNumberOfNodes();

			System.out.println("Number Of Nodes = " + intNumberOfNodes);
			Logger.getLogger().logger("Number Of Nodes = " + intNumberOfNodes);
			
			System.out.println("------------------------------------------------------------------");
			System.out.println("NODE LOCATION LIST :-");
			
			Logger.getLogger().logger("------------------------------------------------------------------");
			Logger.getLogger().logger("NODE LOCATION LIST :-");
			
			Map<Integer, NodeBO> mapNodeBo 					= objConfigBO.getMapNodeBO();
			for(Integer intNodeIndex : mapNodeBo.keySet()) {
				
				NodeBO objNodeBO							= mapNodeBo.get(intNodeIndex);
				
				System.out.println("NODE " + intNodeIndex + " -> " + objNodeBO.toString());
				Logger.getLogger().logger("NODE " + intNodeIndex + " -> " + objNodeBO.toString());
			}
			
			System.out.println("------------------------------------------------------------------");
			System.out.println("NODE PATH LIST :-");
			
			Logger.getLogger().logger("------------------------------------------------------------------");
			Logger.getLogger().logger("NODE PATH LIST :-");
			
			Map<Integer, ArrayList<Integer>> mapNodePath	= objConfigBO.getMapNodePath();
			for(Integer intNodeIndex : mapNodePath.keySet()) {
				
				ArrayList<Integer> lstNodePath				= mapNodePath.get(intNodeIndex);
				
				String strNodePath							= ("NODE " + intNodeIndex + " PATH -> ");
				boolean blnFlagFirstIndex					= true;
				for(Integer intNodePath : lstNodePath) {
					
					if(blnFlagFirstIndex) {

						blnFlagFirstIndex					= false;
						strNodePath							+= (" " + intNodePath);
					}
					else {
						
						strNodePath							+= (" -> " + intNodePath);
					}
				}
				
				System.out.println(strNodePath);
				Logger.getLogger().logger(strNodePath);
			}
		
		} catch (PropertyException e) {
			e.printStackTrace();
		} catch (ConfigException e) {
			e.printStackTrace();
		} catch (ParsingException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}*/
}
