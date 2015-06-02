package br.edu.ufcg.embedded.projectmanager.business;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.ufcg.embedded.projectmanager.domain.Project;
import br.edu.ufcg.embedded.projectmanager.domain.User;
import br.edu.ufcg.embedded.projectmanager.exception.EventException;
import br.edu.ufcg.embedded.projectmanager.listener.EventListener;

public class Bus {
	
	private static Map<String, List<EventListener> > mapListeners; 
	
	public final static String PROJECT_CREATED = "project_created";
	public final static String USER_CREATED = "user_created";
	
	public Bus() {
		mapListeners = new HashMap<String, List<EventListener>>();
	}
	
	public void addListeners(String eventType, List<EventListener> listeners) {
		mapListeners.put(eventType, listeners);
	}
	
	public static void fireProjectEvent(String eventType, Project project) throws EventException {
		for(EventListener listener : mapListeners.get(eventType)) {
			listener.projectCreated(project);
		}
	}
	
	public static void fireUserEvent(String eventType, User user) throws EventException {
		for(EventListener listener : mapListeners.get(eventType)) {
			listener.userCreated(user);
		}
	}
	
}
