package br.edu.ufcg.embedded.projectmanager.generic;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

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

@Service
public abstract class GenericServiceImpl<T, R extends 
	    JpaRepository<T, Integer>> implements GenericService<T> {

	@Autowired
	protected R repository;

	@Transactional
	@Override
	public CreateResponseEvent<T> request(
			@Valid CreateRequestEvent<T> request) {

		T object = repository.saveAndFlush(request.getObject());
		
		return new CreateResponseEvent<T>(
				object);
	}

	@Override
	public ListResponseEvent<T> request(ListRequestEvent<T> request) {
		List<T> objects = repository.findAll();
		return new ListResponseEvent<T>(objects);
	}

	@Transactional
	@Override
	public UpdateResponseEvent<T> request(
			@Valid UpdateRequestEvent<T> request) {

		UpdateResponseEvent<T> response = new UpdateResponseEvent<T>();

		T object = repository.findOne(request.getId());

		if (object == null) {
			response.setEntityFound(false);
		} else {
			T updatedObject = repository.saveAndFlush(request.getObject());
			response.setObject(updatedObject);
		}

		return response;
	}

	@Override
	public ViewResponseEvent<T> request(ViewRequestEvent<T> request) {
		T object = repository.findOne(request.getId());

		ViewResponseEvent<T> response = new ViewResponseEvent<T>(
				request.getId(), object);

		if (object == null) {
			response.setEntityFound(false);
		}
		return response;
	}

	@Transactional
	@Override
	public DeleteResponseEvent<T> request(DeleteRequestEvent request) {

		T object = repository.findOne(request.getId());

		DeleteResponseEvent<T> response = new DeleteResponseEvent<T>(
				object);

		if (object == null) {
			response.setEntityFound(false);
		} else {
			response.setObject(object);
			repository.delete(request.getId());
		}

		return response;

	}

}
