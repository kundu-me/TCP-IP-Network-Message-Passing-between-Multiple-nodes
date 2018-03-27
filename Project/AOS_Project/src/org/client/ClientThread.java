package org.client;

import java.util.List;

import org.bo.ConfigBO;
import org.bo.TokenBO;
import org.exception.ConnectionException;

/**
 * 
 * @author	Nirmallya Kundu
 * @id	 	nxk161830 
 * @email 	nxk161830@utdallas.edu
 *
 */
public class ClientThread implements Runnable{

	private int intNodeIdentifier;
	private int intNodeLabelValue;

	public ClientThread(int intNodeIdentifier, int intNodeLabelValue) {

		this.intNodeIdentifier 			= intNodeIdentifier;
		this.intNodeLabelValue			= intNodeLabelValue;
	}

	@Override
	public void run() {

		List<Integer> lstNodePath		= ConfigBO.getSingletonInstance().getMapNodePath().get(intNodeIdentifier);

		if((null != lstNodePath) && (lstNodePath.size() > 1)) {

			int intCurrentNodeIdentifier= lstNodePath.get(0);
			TokenBO objClientTokenBO	= new TokenBO(intCurrentNodeIdentifier, intNodeLabelValue);

			System.out.println("objClientTokenBO = [" + intNodeIdentifier + "] =" + objClientTokenBO);

			Client objClient		= new Client(objClientTokenBO);
			try {

				objClient.connectToServer();
			} 
			catch (ConnectionException e) {

				e.printStackTrace();
			}

		}
		else {

			Thread.currentThread().interrupt();
			return;
		}
	}
}
