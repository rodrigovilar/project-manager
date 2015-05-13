package br.edu.ufcg.embedded.projectmanager.generic.event;

public class DeleteRequestEvent {

	private Integer id;

	public DeleteRequestEvent(Integer id) {
		super();
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
