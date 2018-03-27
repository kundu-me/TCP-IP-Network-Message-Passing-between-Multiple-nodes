package org.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.bo.ProjectConstants;
import org.bo.TokenBO;
import org.exception.FileWriteException;
import org.exception.PropertyException;
import org.logger.Logger;
/**
 * 
 * @author	Nirmallya Kundu
 * @id	 	nxk161830 
 * @email 	nxk161830@utdallas.edu
 *
 */
public class ProjectUtil {

	private static Properties objProperties			= null;
	private static String strTimeStamp				= null;
	private static boolean blnFlagPrintLog			= false;
	private static boolean blnFlagWriteLog			= false;

	private static boolean blnFlagPrintWriteCheck	= false;

	public static int getRandomNumber(int min, int max) throws IllegalArgumentException {

		if (min >= max) {

			String strLoggerMessage			= "Max must be greater than Min";
			System.out.println(strLoggerMessage);
			throw new IllegalArgumentException(strLoggerMessage);
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}


	public static String getProperty(String strPropertyKey) throws PropertyException {

		InputStream objInputStream			= null;
		String strPropertyValue				= null;

		try {

			if(null == objProperties) {

				objInputStream					= new FileInputStream(System.getProperty("user.dir") + "/workspace/AOS_Project_1/properties/config.properties");

				objProperties					= new Properties();
				objProperties.load(objInputStream);
			}

			strPropertyValue					= objProperties.getProperty(strPropertyKey);
			strPropertyValue					= (null != strPropertyValue)? strPropertyValue.trim() : strPropertyValue;

			if(null == strPropertyValue) {

				String strLoggerMessage			= "No Property Key Found for the Key = " + strPropertyKey;
				System.out.println(strLoggerMessage);
				throw new PropertyException(strLoggerMessage);
			}
			else if(strPropertyValue.length() == 0) {

				String strLoggerMessage			= "No Property Value found with the Key = " + strPropertyKey;
				System.out.println(strLoggerMessage);
				throw new PropertyException(strLoggerMessage);
			}

		}
		catch(FileNotFoundException e) {

			String strLoggerMessage				= "FileNotFoundException : " + e.getMessage();
			System.out.println(strLoggerMessage);
			e.printStackTrace();
			throw new PropertyException(strLoggerMessage);
		}
		catch(Exception e) {

			String strLoggerMessage				= "Exception : " + e.getMessage();
			System.out.println(strLoggerMessage);
			e.printStackTrace();
			throw new PropertyException(strLoggerMessage);
		}
		finally {

			if (null != objInputStream) {

				try {

					objInputStream.close();
				}
				catch (IOException e) {

					String strLoggerMessage		= "IOException : " + e.getMessage();
					System.out.println(strLoggerMessage);
					e.printStackTrace();
					throw new PropertyException(strLoggerMessage);
				}
				catch(Exception e) {

					String strLoggerMessage		= "Exception : " + e.getMessage();
					System.out.println(strLoggerMessage);
					e.printStackTrace();
					throw new PropertyException(strLoggerMessage);
				}
			}
		}

		return strPropertyValue;
	}

	public static void waiting(long lngWaitTimeInMillis) {

		long lngStartTime					= System.currentTimeMillis();
		long lngEndTime						= lngStartTime + lngWaitTimeInMillis;

		while (System.currentTimeMillis()  < lngEndTime) {

		}
	}

	public static void writeFinalTokenToFile(int intNodeLabelValue, TokenBO objTokenBO) throws PropertyException, FileWriteException {


		System.out.println("Writing OutputFile : TokenBO -> " + objTokenBO);

		Logger.getLogger().logger("Writing OutputFile : TokenBO -> " + objTokenBO);

		String strFileContent			= "" + intNodeLabelValue + "\t" + objTokenBO.getIntSumLabelValues() + "";


		String strOutputFileNameInitial	= ProjectUtil.getProperty(ProjectConstants.PROP_KEY_OUTPUT_FILE_NAME_INITIAL);
		String strOutputFileType		= ProjectUtil.getProperty(ProjectConstants.PROP_KEY_OUTPUT_FILE_TYPE);

		String strOutputFile			= ProjectUtil.getOutputFilePath(ProjectConstants.FILE_TYPE_OUTPUT + objTokenBO.getIntNodeIdentifier()) + File.separator + strOutputFileNameInitial + objTokenBO.getIntNodeIdentifier() + strOutputFileType;

		File objFile					= new File(strOutputFile);
		FileWriter objFileWriter 		= null;
		BufferedWriter objBufferedWriter= null;

		try {

			if (!objFile.exists()) {

				objFile.createNewFile();
			}

			objFileWriter 				= new FileWriter(objFile.getAbsoluteFile());
			objBufferedWriter 			= new BufferedWriter(objFileWriter);
			objBufferedWriter.write(strFileContent);
		}
		catch(IOException e) {

			String strLoggerMessage		= "IOException : " + e.getMessage();
			System.out.println(strLoggerMessage);
			e.printStackTrace();
			throw new FileWriteException(strLoggerMessage);
		}
		catch(Exception e) {

			String strLoggerMessage		= "Exception : " + e.getMessage();
			System.out.println(strLoggerMessage);
			e.printStackTrace();
			throw new FileWriteException(strLoggerMessage);
		}
		finally {

			try {

				if(null != objBufferedWriter) {

					objBufferedWriter.close();
				}
			}
			catch(IOException e) {

				String strLoggerMessage		= "IOException : " + e.getMessage();
				System.out.println(strLoggerMessage);
				e.printStackTrace();
				throw new FileWriteException(strLoggerMessage);
			}
			catch(Exception e) {

				String strLoggerMessage		= "Exception : " + e.getMessage();
				System.out.println(strLoggerMessage);
				e.printStackTrace();
				throw new FileWriteException(strLoggerMessage);
			}
		}

		System.out.println("Writing Completed : strOutputFile = " + strOutputFile);

		Logger.getLogger().logger("Writing Completed : strOutputFile = " + strOutputFile);
	}

	/*public static boolean isHostAvailable(String strHostName, int intPort){ 

		Socket objSocket					= null;
		boolean blnIsHostAvailavle			= false;

		try {

			objSocket						= new Socket(strHostName, intPort);
			if((null != objSocket) && (objSocket.isConnected())) {

				blnIsHostAvailavle			= true;
				objSocket.close();
			}
		}
		catch (UnknownHostException e) { 

			blnIsHostAvailavle				= false;
			objSocket 						= null;
		} 
		catch (IOException e) {

			blnIsHostAvailavle				= false;
			objSocket 						= null;
		} 
		catch (NullPointerException e) {

			blnIsHostAvailavle				= false;
			objSocket 						= null;
		}
		catch(Exception e) {


			blnIsHostAvailavle				= false;
			objSocket 						= null;
		}

		objSocket 							= null;

		System.out.println("[HostName, Port] = [" + strHostName + ", " + intPort + "] isAvailable = " + blnIsHostAvailavle);

		Logger.getLogger().logger("[HostName, Port] = [" + strHostName + ", " + intPort + "] isAvailable = " + blnIsHostAvailavle);

		return blnIsHostAvailavle;
	}*/

	public static String printNodePath(int intNodeIdentifier, List<Integer> lstNodePath) {

		String strNodePath					= ("Node Path For NodeIdentifier [ " + intNodeIdentifier + "] : [ ");

		if(null == lstNodePath) {

			strNodePath						+= ("NULL ]");
		}

		else {

			boolean blnStartPath						= true;
			for(Integer currentNodeIdentifier : lstNodePath) {

				if(blnStartPath) {

					blnStartPath						= false;
					strNodePath							+= (currentNodeIdentifier);
				}
				else {

					strNodePath							+= (" -> " + currentNodeIdentifier);
				}
			}

			strNodePath									+= (" ]");
		}

		return strNodePath;
	}

	public static String getStrTimeStamp() {

		if(null == strTimeStamp) {

			Calendar objCalendar			= Calendar.getInstance();
			strTimeStamp					= (objCalendar.get(Calendar.MONTH) + 1) + "." + (objCalendar.get(Calendar.DATE)) + "." + (objCalendar.get(Calendar.YEAR));
			strTimeStamp					= strTimeStamp + "_" + (objCalendar.getTime().getTime());
		}

		return strTimeStamp;
	}


	public static String getOutputFilePath(String strFolderName) throws PropertyException {

		String strOutputFilePath		= System.getProperty("user.dir") + ProjectUtil.getProperty(ProjectConstants.PROP_KEY_OUTPUT_FILE_PATH);
		//strOutputFilePath				= strOutputFilePath + File.separator + ProjectUtil.getStrTimeStamp() + File.separator + strFolderName;
		strOutputFilePath				= strOutputFilePath + File.separator + strFolderName;
		File objFileDir					= new File(strOutputFilePath);
		if(objFileDir.isDirectory() == false) {

			objFileDir.mkdirs();
		}

		return strOutputFilePath; 
	}

	public static boolean getBlnFlagPrintLog() {

		getBlnFlagPrintWriteLog();
		return blnFlagPrintLog;
	}

	public static boolean getBlnFlagWriteLog() {

		getBlnFlagPrintWriteLog();
		return blnFlagWriteLog;
	}

	private static void getBlnFlagPrintWriteLog() {

		if(blnFlagPrintWriteCheck == true) {

			return;
		}

		try {

			String strFlagPrintLog		= ProjectUtil.getProperty(ProjectConstants.PROP_KEY_FLAG_PRINT_LOG);

			if(null != strFlagPrintLog) {

				if(("TRUE").equalsIgnoreCase(strFlagPrintLog)
						|| ("1").equalsIgnoreCase(strFlagPrintLog)) {

					blnFlagPrintLog		= true;
				}
			}
		}
		catch (PropertyException e) {

			blnFlagPrintLog				= false;
			e.printStackTrace();
		}

		try {

			String strFlagWriteLog		= ProjectUtil.getProperty(ProjectConstants.PROP_KEY_FLAG_WRITE_LOG);

			if(null != strFlagWriteLog) {

				if(("TRUE").equalsIgnoreCase(strFlagWriteLog)
						|| ("1").equalsIgnoreCase(strFlagWriteLog)) {

					blnFlagWriteLog		= true;
				}
			}
		}
		catch (PropertyException e) {

			blnFlagWriteLog				= false;
			e.printStackTrace();
		}

		blnFlagPrintWriteCheck			= true;
	}


	public static int getRandomNodeValue() {

		int intRandomNodeValue			= 0;
		try {

			int intMinNodeValue		= Integer.parseInt(ProjectUtil.getProperty(ProjectConstants.PROP_KEY_NODE_VALUE_MIN));
			int intMaxNodeValue		= Integer.parseInt(ProjectUtil.getProperty(ProjectConstants.PROP_KEY_NODE_VALUE_MAX));
			intRandomNodeValue		= ProjectUtil.getRandomNumber(intMinNodeValue, intMaxNodeValue);
		}
		catch(NumberFormatException e) {

			String strLoggerMessage				= "NumberFormatException : " + e.getMessage();
			System.out.println(strLoggerMessage);
			e.printStackTrace();
		}
		catch(IllegalArgumentException e) {

			String strLoggerMessage				= "IllegalArgumentException : " + e.getMessage();
			System.out.println(strLoggerMessage);
			e.printStackTrace();
		}
		catch(PropertyException e) {

			String strLoggerMessage				= "PropertyException : " + e.getMessage();
			System.out.println(strLoggerMessage);
			e.printStackTrace();
		}
		catch(Exception e) {

			String strLoggerMessage				= "Exception : " + e.getMessage();
			System.out.println(strLoggerMessage);
			e.printStackTrace();
		}
		
		return intRandomNodeValue;
	}
}

