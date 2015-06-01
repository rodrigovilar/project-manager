package br.edu.ufcg.embedded.projectmanager.listener;

import java.util.ArrayList;
import java.util.List;

import br.edu.ufcg.embedded.projectmanager.business.Bus;

public class ListenersController {
	
	
	public static void startListeners() {
		Bus bus = new Bus();
		List<ProjectListener> projectCreated = projectCreatedListeners();
		bus.addListeners(Bus.PROJECT_CREATED, projectCreated);
	}
	
	
	private static List<ProjectListener> projectCreatedListeners() {
		List<ProjectListener> listeners = new ArrayList<ProjectListener>();
		//listeners.add(new TuleapListener());
		listeners.add(new TestLinkListener());
		//listeners.add(new GitLabListener());
		//listeners.add(new JenkinsListener());
		return listeners;
	}
	
	
}
