package br.edu.ufcg.embedded.projectmanager.listener;

import br.edu.ufcg.embedded.projectmanager.domain.Project;
import br.edu.ufcg.embedded.projectmanager.domain.User;
import br.edu.ufcg.embedded.projectmanager.exception.EventException;

public interface EventListener {
	
	public void projectCreated(Project project) throws EventException;
	
	public void userCreated(User user) throws EventException;

}
