package org.logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.bo.ProjectConstants;
import org.util.ProjectUtil;

/**
 * 
 * @author	Nirmallya Kundu
 * @id	 	nxk161830 
 * @email 	nxk161830@utdallas.edu
 *
 */
public class Logger {

	private static File		objLogFile;
	private static Logger	logger;
	
	private Logger() {

	}

	public static Logger getLogger() {

		if(null == logger) {
			
			logger 				= new Logger();
			objLogFile			= getLogFile();
		}

		return logger;
	}

	private static File getLogFile() {

		if(null != objLogFile) {

			return objLogFile;
		}

		try {

			String strLogFileName			= ProjectUtil.getProperty(ProjectConstants.PROP_KEY_LOG_FILE_NAME);
			String strOutputFileType		= ProjectUtil.getProperty(ProjectConstants.PROP_KEY_OUTPUT_FILE_TYPE);

			String strOutputFile			= ProjectUtil.getOutputFilePath(ProjectConstants.FILE_TYPE_LOGS) + File.separator + strLogFileName + strOutputFileType;

			objLogFile						= new File(strOutputFile);

			if (!objLogFile.exists()) {

				objLogFile.createNewFile();
			}
		}
		catch(IOException e) {

			String strLoggerMessage		= "IOException : " + e.getMessage();
			System.out.println(strLoggerMessage);
			e.printStackTrace();
		}
		catch(Exception e) {

			String strLoggerMessage		= "Exception : " + e.getMessage();
			System.out.println(strLoggerMessage);
			e.printStackTrace();
		}

		return objLogFile;

	}

	public void logger(String strLogs) {

		if(ProjectUtil.getBlnFlagPrintLog() == true) {
			
			System.out.println(strLogs);
		}
		
		if(ProjectUtil.getBlnFlagWriteLog() == false) {
			
			return;
		}
		
		if(strLogs.length() > 0) {
			
			return;
		}
		
		FileWriter objFileWriter				= null;
		BufferedWriter objBufferedWriter		= null;
		try {

			objLogFile							= Logger.getLogFile();
			objFileWriter						= new FileWriter(objLogFile.getAbsoluteFile(), true);
			objBufferedWriter					= new BufferedWriter(objFileWriter);
			
			String strLogMessageContent			= strLogs;	
			objBufferedWriter.write(strLogMessageContent);
			objBufferedWriter.newLine();
		}
		catch(IOException e) {

			String strLoggerMessage		= "IOException : " + e.getMessage();
			System.out.println(strLoggerMessage);
			e.printStackTrace();
		}
		catch(Exception e) {

			String strLoggerMessage		= "Exception : " + e.getMessage();
			System.out.println(strLoggerMessage);
			e.printStackTrace();
		}
		finally {
			
			try {
				
				if(null != objBufferedWriter) {
					
					objBufferedWriter.close();
					objBufferedWriter	= null;
				}
			}
			catch(IOException e) {

				String strLoggerMessage		= "IOException : " + e.getMessage();
				System.out.println(strLoggerMessage);
				e.printStackTrace();
			}
			catch(Exception e) {

				String strLoggerMessage		= "Exception : " + e.getMessage();
				System.out.println(strLoggerMessage);
				e.printStackTrace();
			}

		}
		
	}
	
}

