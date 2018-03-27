package org.main;

import org.bo.ConfigBO;
import org.factory.ProjectFactory;
import org.service.api.ProjectServiceAPI;
import org.util.ConfigParserUtil;
import org.util.ProjectUtil;

public class ProjectMain {

	/**
	 * 
	 * @param args
	 */
	public void projectMain(String[] args) {

		if((null != args) 
				&& (args.length >= 2)
				&& (null != args[0])
				&& (null != args[1])) {

			String strNodeIdentifier		= (args[0]).trim();
			String strNodePort				= (args[1]).trim();

			try {
				
				System.out.println("### Started Parsing Config File...For NODE->" + strNodeIdentifier);
				
				ConfigBO	objConfigBO				= ConfigBO.getSingletonInstance();
				ProjectServiceAPI objService		= ProjectFactory.getProjectService();
				
				int intNodeLabelValue				= ProjectUtil.getRandomNodeValue();
				
				System.out.println("### Config File Parsed Successfully For NODE->" + strNodeIdentifier);

				int intNodeIdentifier 		= Integer.parseInt(strNodeIdentifier);
				int intNodePort 			= Integer.parseInt(strNodePort);

				System.out.println("### Preparing to start the Server on Node -> [intNodeIdentifier = " + intNodeIdentifier + ", intNodePort = " + intNodePort + "]");
				objService.runTheServer(objConfigBO, intNodeIdentifier, intNodePort, intNodeLabelValue);

				ProjectUtil.waiting(5000);

				System.out.println("### Preparing to start the Client on Node -> [intNodeIdentifier = " + intNodeIdentifier + ", intNodePort = " + intNodePort + "]");
				objService.runTheClient(objConfigBO, intNodeIdentifier, intNodePort, intNodeLabelValue);
			} 
			catch(NumberFormatException e) {

				e.printStackTrace();
			}
			catch (Exception e) {

				e.printStackTrace();
			}

		}

	}

	public static void main(String[] args) {

		ProjectMain objMain					= new ProjectMain();
		objMain.projectMain(args);
	}
}
