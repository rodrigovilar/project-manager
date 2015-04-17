package br.edu.ufcg.embedded.projectmanager.generic.event;



public class ViewResponseEvent<T> extends ResponseEvent {

	private Integer id;
	private T object;

	public ViewResponseEvent(Integer id, T object) {
		super();
		this.id = id;
		this.object = object;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public T getObject() {
		return object;
	}

}
