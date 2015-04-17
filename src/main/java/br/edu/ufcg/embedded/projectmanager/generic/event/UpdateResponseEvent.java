package br.edu.ufcg.embedded.projectmanager.generic.event;



public class UpdateResponseEvent<T> extends ResponseEvent {

	private T object;

	public UpdateResponseEvent() {
	}

	public UpdateResponseEvent(T object) {
		super();
		this.object = object;
	}

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}

}
