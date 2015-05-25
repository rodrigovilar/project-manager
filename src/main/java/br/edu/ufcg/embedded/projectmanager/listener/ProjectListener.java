package br.edu.ufcg.embedded.projectmanager.listener;

import br.edu.ufcg.embedded.projectmanager.domain.Project;
import br.edu.ufcg.embedded.projectmanager.exception.ProjectException;

public interface ProjectListener {
	
	public void projectCreated(Project project) throws ProjectException;

}
