package br.edu.ufcg.embedded.projectmanager.listener;

import br.edu.ufcg.embedded.projectmanager.client.TestLinkClient;
import br.edu.ufcg.embedded.projectmanager.domain.Project;
import br.edu.ufcg.embedded.projectmanager.exception.ProjectException;

public class TestLinkListener implements ProjectListener {

	@Override
	public void projectCreated(Project project) throws ProjectException {
		TestLinkClient.instance().createTestLinkProject(project);
	}

}
