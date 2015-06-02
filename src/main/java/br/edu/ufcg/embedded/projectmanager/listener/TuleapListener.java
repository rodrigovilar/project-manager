package br.edu.ufcg.embedded.projectmanager.listener;

import br.edu.ufcg.embedded.projectmanager.client.TuleapClient;
import br.edu.ufcg.embedded.projectmanager.domain.Project;
import br.edu.ufcg.embedded.projectmanager.domain.User;
import br.edu.ufcg.embedded.projectmanager.exception.EventException;

public class TuleapListener implements EventListener {

	@Override
	public void projectCreated(Project project) throws EventException {
		TuleapClient.createTuleapProject(project);		
	}

	@Override
	public void userCreated(User user) throws EventException {
		// TODO Auto-generated method stub
		
	}

}
