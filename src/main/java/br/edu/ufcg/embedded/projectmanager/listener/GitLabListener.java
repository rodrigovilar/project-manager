package br.edu.ufcg.embedded.projectmanager.listener;

import br.edu.ufcg.embedded.projectmanager.client.GitLabClient;
import br.edu.ufcg.embedded.projectmanager.domain.Project;
import br.edu.ufcg.embedded.projectmanager.exception.ProjectException;

public class GitLabListener implements ProjectListener {

	@Override
	public void projectCreated(Project project) throws ProjectException {
		GitLabClient.createGitLabProject(project);		
	}

}
