package org.service.impl;

import java.util.Map;

import org.bo.ConfigBO;
import org.bo.NodeBO;
import org.client.ClientThread;
import org.server.Server;
import org.service.api.ProjectServiceAPI;
/**
 * 
 * @author	Nirmallya Kundu
 * @id	 	nxk161830 
 * @email 	nxk161830@utdallas.edu
 *
 */
public class ProjectServiceImpl implements ProjectServiceAPI{

	@Override
	public void runTheServer(ConfigBO objConfigBO, int intNodeIdentifier, int intPort, int intNodeLabelValue) {

		Map<Integer, NodeBO> mapNodeBO			= objConfigBO.getMapNodeBO();

		if(null == mapNodeBO) {
			return;
		}

		NodeBO objNodeBO						= mapNodeBO.get(intNodeIdentifier);

		if((null != objNodeBO) 
				&& (objNodeBO.getIntNodeIdentifier() == intNodeIdentifier)
				&& (objNodeBO.getIntPort() == intPort)) {

			Server objServer				= new Server(intNodeIdentifier, intNodeLabelValue);
			Thread thread					= new Thread(objServer);
			thread.start();
		}
	}

	@Override
	public void runTheClient(ConfigBO objConfigBO, int intNodeIdentifier, int intPort, int intNodeLabelValue) {

		Map<Integer, NodeBO> mapNodeBO		= objConfigBO.getMapNodeBO();

		if(null == mapNodeBO) {
			return;
		}

		NodeBO objNodeBO					= mapNodeBO.get(intNodeIdentifier);

		if((null != objNodeBO) 
				&& (objNodeBO.getIntNodeIdentifier() == intNodeIdentifier)
				&& (objNodeBO.getIntPort() == intPort)) {
			System.out.println("Sending first token");

			ClientThread objClientThread	= new ClientThread(intNodeIdentifier, intNodeLabelValue);
			Thread thread					= new Thread(objClientThread);
			thread.start();
		}

	}
}