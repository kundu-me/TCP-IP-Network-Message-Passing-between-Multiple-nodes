package org.server;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;

import org.bo.ConfigBO;
import org.bo.NodeBO;
import org.logger.Logger;
/**
 * 
 * @author	Nirmallya Kundu
 * @id	 	nxk161830 
 * @email 	nxk161830@utdallas.edu
 *
 */
public class Server implements Runnable{

	private int								intNodeIdentifier;
	private int 							intNodeLabelValue;
	
	private ServerSocket 					objServerSocket;
	
	/**
	 * 
	 */
	public Server() {

		super();
	}
	
	/**
	 * 
	 * @param objNodeBO
	 */
	public Server(int intNodeIdentifier, int intNodeLabelValue) {

		this.intNodeIdentifier				= intNodeIdentifier;
		this.intNodeLabelValue				= intNodeLabelValue;
	}

	/**
	 * 
	 */
	@Override
	public void run() {

		this.runTheServer();
	}

	/**
	 * 
	 * @param objNodeBO
	 */
	
	public void runTheServer() {
		

		NodeBO objNodeBO				= ConfigBO.getSingletonInstance().getMapNodeBO().get(intNodeIdentifier);
		objNodeBO.setIntNodeLabelValue(intNodeLabelValue);
		
		System.out.println("Starting Server On : Node -> " + objNodeBO.toString());
		
		Logger.getLogger().logger("Starting Server On : Node -> " + objNodeBO.toString());
		
		try {
			
			try {
				
				objServerSocket				= new ServerSocket(objNodeBO.getIntPort());
			}
			catch(BindException e) {
				
				e.printStackTrace();
			}
			
			
			if(null != objServerSocket) {
				
				System.out.println("Server Started Successfully On Node -> " + objNodeBO.toString());
				
				Logger.getLogger().logger(("Server Started Successfully On Node -> " + objNodeBO.toString()));
			}
			
			while(true)	{
					
				ServerThread objServerThread	= new ServerThread(objServerSocket.accept(), intNodeIdentifier, intNodeLabelValue);
				Thread thread					= new Thread(objServerThread);
				thread.start();
			}

		}
		catch(IOException e) {
			
			Logger.getLogger().logger("Server Starting Failed On Node -> " + objNodeBO.toString());
			
			e.printStackTrace();
		}
		catch(Exception e) {
			
			Logger.getLogger().logger("Server Starting Failed On Node -> " + objNodeBO.toString());
			
			e.printStackTrace();
		}
	}

	
	/**
	 * 
	 */
	@Override
	public String toString() {
		return "Server [intNodeIdentifier=" + intNodeIdentifier + "]";
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
