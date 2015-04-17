package br.edu.ufcg.embedded.projectmanager.generic.event;

public class UpdateRequestEvent<T> {

	private Integer id;
	private T object;

	public UpdateRequestEvent(Integer id, T obejct) {
		super();
		this.id = id;
		this.object = obejct;
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
