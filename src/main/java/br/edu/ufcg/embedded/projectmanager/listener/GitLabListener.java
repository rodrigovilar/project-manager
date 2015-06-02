package br.edu.ufcg.embedded.projectmanager.listener;

import br.edu.ufcg.embedded.projectmanager.client.GitLabClient;
import br.edu.ufcg.embedded.projectmanager.domain.Project;
import br.edu.ufcg.embedded.projectmanager.domain.User;
import br.edu.ufcg.embedded.projectmanager.exception.EventException;

public class GitLabListener implements EventListener {

	@Override
	public void projectCreated(Project project) throws EventException {
		GitLabClient.instance().createGitLabProject(project);		
	}

	@Override
	public void userCreated(User user) throws EventException {
		// TODO Auto-generated method stub
		
	}

}
