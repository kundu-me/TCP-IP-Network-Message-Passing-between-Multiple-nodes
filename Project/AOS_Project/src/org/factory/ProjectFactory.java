package org.factory;

import org.service.api.ProjectServiceAPI;
import org.service.impl.ProjectServiceImpl;
/**
 * 
 * @author	Nirmallya Kundu
 * @id	 	nxk161830 
 * @email 	nxk161830@utdallas.edu
 *
 */
public class ProjectFactory {

	private static ProjectServiceImpl objProjectService;
	
	/**
	 * 
	 * @return
	 */
	public static ProjectServiceAPI getProjectService() {
		
		if(null == objProjectService) {
			
			objProjectService	= new ProjectServiceImpl();
		}
		
		return objProjectService;
	}
}
