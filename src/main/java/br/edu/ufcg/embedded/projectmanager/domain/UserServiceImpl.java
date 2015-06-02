package br.edu.ufcg.embedded.projectmanager.domain;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.stereotype.Service;

import br.edu.ufcg.embedded.projectmanager.business.Bus;
import br.edu.ufcg.embedded.projectmanager.data.UserRepository;
import br.edu.ufcg.embedded.projectmanager.exception.EventException;
import br.edu.ufcg.embedded.projectmanager.generic.GenericServiceImpl;
import br.edu.ufcg.embedded.projectmanager.generic.event.CreateRequestEvent;
import br.edu.ufcg.embedded.projectmanager.generic.event.CreateResponseEvent;

@Service
public class UserServiceImpl extends GenericServiceImpl<User, UserRepository>
							implements UserService {

	@Transactional
	@Override
	public CreateResponseEvent<User> request(
			@Valid CreateRequestEvent<User> request) throws EventException {
		
		User object = repository.saveAndFlush(request.getObject());
		
		Bus.fireUserEvent(Bus.USER_CREATED, object);
		
		return new CreateResponseEvent<User>(object);		
	}
	
}
