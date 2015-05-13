package br.edu.ufcg.embedded.projectmanager.generic.event;

public class CreateResponseEvent<T> {

	private T object;

	public CreateResponseEvent(T object) {
		super();
		this.object = object;
	}

	public CreateResponseEvent() {
	}

	public T getObject() {
		return object;
	}	

}
