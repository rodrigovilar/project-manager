package br.edu.ufcg.embedded.projectmanager.generic;

import br.edu.ufcg.embedded.projectmanager.exception.ProjectException;
import br.edu.ufcg.embedded.projectmanager.generic.event.CreateRequestEvent;
import br.edu.ufcg.embedded.projectmanager.generic.event.CreateResponseEvent;
import br.edu.ufcg.embedded.projectmanager.generic.event.DeleteRequestEvent;
import br.edu.ufcg.embedded.projectmanager.generic.event.DeleteResponseEvent;
import br.edu.ufcg.embedded.projectmanager.generic.event.ListRequestEvent;
import br.edu.ufcg.embedded.projectmanager.generic.event.ListResponseEvent;
import br.edu.ufcg.embedded.projectmanager.generic.event.UpdateRequestEvent;
import br.edu.ufcg.embedded.projectmanager.generic.event.UpdateResponseEvent;
import br.edu.ufcg.embedded.projectmanager.generic.event.ViewRequestEvent;
import br.edu.ufcg.embedded.projectmanager.generic.event.ViewResponseEvent;


public interface GenericService<T> {

	public CreateResponseEvent<T> request(CreateRequestEvent<T> request) throws ProjectException;
	
	public ListResponseEvent<T> request(ListRequestEvent<T> request);

	public UpdateResponseEvent<T> request(UpdateRequestEvent<T> request);

	public ViewResponseEvent<T> request(ViewRequestEvent<T> request);

	public DeleteResponseEvent<T> request(DeleteRequestEvent request);

}
