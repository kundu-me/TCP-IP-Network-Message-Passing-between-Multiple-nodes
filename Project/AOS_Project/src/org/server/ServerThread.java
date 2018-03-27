package org.server;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;

import org.bo.TokenBO;
import org.client.Client;
import org.exception.FileWriteException;
import org.exception.PropertyException;
import org.util.ProjectUtil;
/**
 * 
 * @author	Nirmallya Kundu
 * @id	 	nxk161830 
 * @email 	nxk161830@utdallas.edu
 *
 */
public class ServerThread implements Runnable{

	private int								intNodeIdentifier;
	private int								intNodeLabelValue;

	private Socket		 					objSocketOfServer;
	@SuppressWarnings("unused")
	private ObjectInputStream 				objInputStream;

	/**
	 * 
	 */
	public ServerThread() {

		super();
	}

	public ServerThread(Socket objSocketOfServer, int intNodeIdentifier, int intNodeLabelValue) {

		this.intNodeIdentifier				= intNodeIdentifier;
		this.intNodeLabelValue				= intNodeLabelValue;
		
		this.objSocketOfServer				= objSocketOfServer;
	}

	/**
	 * 
	 */
	@Override
	public void run() {

		this.runServer();
	}

	/**
	 * 
	 * @param objNodeBO
	 */

	public void runServer() {



		try {

			BufferedInputStream objBufferedInputStream			= new BufferedInputStream(objSocketOfServer.getInputStream());
			ObjectInputStream objInputStream 					= new ObjectInputStream(objBufferedInputStream);

			TokenBO objServerTokenBO 							=  (TokenBO) objInputStream.readObject();
			objInputStream.close();
			System.out.println(objServerTokenBO);		

			TokenBO	objProcessedTokenBO							= this.processReceivedToken(objServerTokenBO);

			if(objServerTokenBO.isBlnFlagTerminationToken() == true) {

				Thread.currentThread().interrupt();
				System.out.println("=======================================> Node = " + intNodeIdentifier + "  COMPLETED <=======================================");
				
				return;
			}

			Client objNextClient 			= new Client(objProcessedTokenBO);
			objSocketOfServer.close();
			objNextClient.connectToServer();

		}
		catch(IOException e) {

			System.out.println("############### -> " + intNodeIdentifier);
			e.printStackTrace();
		}
		catch(Exception e) {

			System.out.println("############### -> " + intNodeIdentifier);
			e.printStackTrace();
		}
	}


	private TokenBO processReceivedToken(TokenBO objTokenBO) {

		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$");
		System.out.println("Before Processing : -> " + objTokenBO);
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$");
		
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&[ " +intNodeIdentifier + " -> " +intNodeLabelValue + "]");
		
		List<Integer> lstNodePath				= objTokenBO.getLstOriginalNodePath();
		if(lstNodePath.size() > 1) {

			System.out.println(objTokenBO);
			objTokenBO.updateIntSumLabelValues(intNodeLabelValue);
			objTokenBO.removeProcessedNodeIdentifier();
		}
		else if(lstNodePath.size() == 1) {

			objTokenBO.removeProcessedNodeIdentifier();
			objTokenBO.setBlnFlagTerminationToken(true);
			try {

				System.out.println("### Writing token for node = [" + objTokenBO.getIntNodeIdentifier() + "] -> " + objTokenBO);
				ProjectUtil.writeFinalTokenToFile(intNodeLabelValue, objTokenBO);
			} 
			catch (PropertyException e) {

				e.printStackTrace();
			} 
			catch (FileWriteException e) {

				e.printStackTrace();
			}
		}
		
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$");
		System.out.println("After Processing : -> " + objTokenBO);
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$");

		return objTokenBO;
	}


	/**
	 * Getters And Setters
	 */

	public int getIntNodeIdentifier() {
		return intNodeIdentifier;
	}

	public void setIntNodeIdentifier(int intNodeIdentifier) {
		this.intNodeIdentifier = intNodeIdentifier;
	}

}
