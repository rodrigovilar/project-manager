package br.edu.ufcg.embedded.projectmanager.business;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.ufcg.embedded.projectmanager.domain.Project;
import br.edu.ufcg.embedded.projectmanager.exception.ProjectException;
import br.edu.ufcg.embedded.projectmanager.listener.ProjectListener;

public class Bus {
	
	private static Map<String, List<ProjectListener> > mapListeners; 
	
	public final static String PROJECT_CREATED = "project_created";
	
	public Bus() {
		mapListeners = new HashMap<String, List<ProjectListener>>();
	}
	
	public void addListeners(String tag, List<ProjectListener> listeners) {
		mapListeners.put(tag, listeners);
	}
	
	public static void fireProjectEvent(String eventType, Project project) throws ProjectException {
		for(ProjectListener listener : mapListeners.get(eventType)) {
			listener.projectCreated(project);
		}
	}
	
}
