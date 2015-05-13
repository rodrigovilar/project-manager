package br.edu.ufcg.embedded.projectmanager.generic.event;


public class ViewRequestEvent<T>  {

	private Integer id;

	public ViewRequestEvent(Integer id) {
		super();
		this.id = id;
	}

	public Integer getId() {
		return id;
	}	

}
