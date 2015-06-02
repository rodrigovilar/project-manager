package br.edu.ufcg.embedded.projectmanager.listener;

import java.util.ArrayList;
import java.util.List;

import br.edu.ufcg.embedded.projectmanager.business.Bus;

public class ListenersController {
	
	
	public static void startListeners() {
		Bus bus = new Bus();
		List<EventListener> projectCreated = projectCreatedListeners();
		bus.addListeners(Bus.PROJECT_CREATED, projectCreated);
	}
	
	
	private static List<EventListener> projectCreatedListeners() {
		List<EventListener> listeners = new ArrayList<EventListener>();
		//listeners.add(new TuleapListener());
		listeners.add(new TestLinkListener());
		//listeners.add(new GitLabListener());
		//listeners.add(new JenkinsListener());
		return listeners;
	}
	
	
}
