package org.service.api;

import org.bo.ConfigBO;
/**
 * 
 * @author	Nirmallya Kundu
 * @id	 	nxk161830 
 * @email 	nxk161830@utdallas.edu
 *
 */
public interface ProjectServiceAPI {

	public void runTheServer(ConfigBO objConfigBO, int intNodeIdentifier, int intPort, int intNodeLabelValue);

	public void runTheClient(ConfigBO objConfigBO, int intNodeIdentifier, int intPort, int intNodeLabelValue);

}
