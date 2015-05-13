package br.edu.ufcg.embedded.projectmanager.generic.event;

import java.util.List;

public class ListResponseEvent<T> {

	private List<T> objects;

	public ListResponseEvent() {
		super();
	}

	public ListResponseEvent(List<T> objects) {
		super();
		this.objects = objects;
	}

	public List<T> getObjects() {
		return objects;
	}

}
