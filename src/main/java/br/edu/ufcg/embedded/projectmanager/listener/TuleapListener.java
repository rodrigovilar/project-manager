package br.edu.ufcg.embedded.projectmanager.listener;

import br.edu.ufcg.embedded.projectmanager.client.TuleapClient;
import br.edu.ufcg.embedded.projectmanager.domain.Project;
import br.edu.ufcg.embedded.projectmanager.exception.ProjectException;

public class TuleapListener implements ProjectListener {

	@Override
	public void projectCreated(Project project) throws ProjectException {
		//TuleapClient.createTuleapProject(project);		
	}

}
