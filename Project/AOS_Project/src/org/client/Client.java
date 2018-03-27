package org.client;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.bo.ConfigBO;
import org.bo.NodeBO;
import org.bo.TokenBO;
import org.exception.ConnectionException;
import org.logger.Logger;

public class Client implements Runnable{

	private TokenBO							objTokenBO;
	
	private Socket 							objClientSocket;
	@SuppressWarnings("unused")
	private ObjectOutputStream 				objOutputStream;

	/**
	 * 
	 */
	public Client() {

		super();
		defaultAssignment();
	}

	/**
	 * 
	 * @param objNodeBO
	 * @param objTokenBO
	 */
	public Client(TokenBO objTokenBO) {

		defaultAssignment();
		
		this.objTokenBO						= objTokenBO;

	}
	
	/**
	 * 
	 */
	private void defaultAssignment() {

		this.objTokenBO						= null;
	}

	/**
	 * 
	 */
	@Override
	public void run() {

		try {

			this.connectToServer();
		} 
		catch (ConnectionException e) {

			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return
	 * @throws ConnectionException
	 */
	public TokenBO connectToServer() throws ConnectionException {

		NodeBO objNodeBO							= ConfigBO.getSingletonInstance().getMapNodeBO().get(objTokenBO.getLstOriginalNodePath().get(0));
		System.out.println("Connecting... : Node -> " + objNodeBO.toString());

		Logger.getLogger().logger("Connecting... : Node -> " + objNodeBO.toString());
		
		String strHostName							= objNodeBO.getStrHostName();
		int intPort									= objNodeBO.getIntPort();

		try {
			
			objClientSocket							= new Socket(strHostName, intPort);
			System.out.println("Connected : Node -> " + objNodeBO.toString());

			try {
				
				BufferedOutputStream objBufferedOutputStream			= new BufferedOutputStream(objClientSocket.getOutputStream());
				ObjectOutputStream objOutputStream = new ObjectOutputStream(objBufferedOutputStream);
				objOutputStream.flush();
				objOutputStream.writeObject(objTokenBO);
				objOutputStream.close();
                System.out.println("client token" + objTokenBO);
			} 
			catch (IOException e) {
				
				e.printStackTrace();
			}    
		} 
		catch(UnknownHostException e) {

			String strLoggerMessage			= "UnknownHostException : For strHostName = " + strHostName + ", intPort = " + intPort + "\n" + e.getMessage();
			System.out.println(strLoggerMessage);
			e.printStackTrace();
			throw new ConnectionException(strLoggerMessage);
		}
		catch(IOException e) {

			String strLoggerMessage			= "IOException : For strHostName = " + strHostName + ", intPort = " + intPort + "\n" + e.getMessage();
			System.out.println(strLoggerMessage);
			e.printStackTrace();
			throw new ConnectionException(strLoggerMessage);
		}
		catch(Exception e) {

			String strLoggerMessage			= "Exception : For strHostName = " + strHostName + ", intPort = " + intPort  + "\n" + e.getMessage();
			System.out.println(strLoggerMessage);
			e.printStackTrace();
			throw new ConnectionException(strLoggerMessage);
		}
		finally {

			try {

				if(null != objClientSocket) {

					objClientSocket.close();
				}

			}
			catch(IOException e) {

				String strLoggerMessage			= "IOException : For strHostName = " + strHostName + ", intPort = " + intPort  + "\n" + e.getMessage();
				System.out.println(strLoggerMessage);
				e.printStackTrace();
				throw new ConnectionException(strLoggerMessage);
			}
			catch(Exception e) {

				String strLoggerMessage			= "Exception : For strHostName = " + strHostName + ", intPort = " + intPort  + "\n" + e.getMessage();
				System.out.println(strLoggerMessage);
				e.printStackTrace();
				throw new ConnectionException(strLoggerMessage);
			}
		}

		return objTokenBO;
	}

	
	/**
	 * 
	 */
	
	
	
	public TokenBO getObjTokenBO() {
		return objTokenBO;
	}

	public void setObjTokenBO(TokenBO objTokenBO) {
		this.objTokenBO = objTokenBO;
	}

}
