package br.edu.ufcg.embedded.projectmanager.generic.event;



public class DeleteResponseEvent<T> extends ResponseEvent {

	private T object;

	public DeleteResponseEvent(T object) {
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
