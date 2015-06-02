package br.edu.ufcg.embedded.projectmanager.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import br.edu.ufcg.embedded.projectmanager.domain.User;
import br.edu.ufcg.embedded.projectmanager.domain.UserService;
import br.edu.ufcg.embedded.projectmanager.exception.EventException;
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


@Controller
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService service;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<UserRest> listUsers() {

		ListResponseEvent<User> response = service
				.request(new ListRequestEvent<User>());

		List<UserRest> users = new ArrayList<UserRest>();

		for (User User : response.getObjects()) {
			users.add(UserRest.fromCore(User));			
		}

		return users;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<UserRest> UserUser(
			@RequestBody UserRest userRest, UriComponentsBuilder builder) {
		CreateRequestEvent<User> request = new CreateRequestEvent<User>(
				userRest.toCore());
		
		CreateResponseEvent<User> response;

		try {
			response = service.request(request);
		} catch (ConstraintViolationException | EventException exception) {
			exception.printStackTrace();
			Logger.getLogger(UserController.class.getSimpleName()).
			log(Level.ALL, exception.getMessage(), exception);
			return new ResponseEntity<UserRest>(HttpStatus.BAD_REQUEST);
		}

		UserRest createdUserRest = UserRest.fromCore(response.getObject());			

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/User/id")
				.buildAndExpand(createdUserRest.getId().toString()).toUri());

		return new ResponseEntity<UserRest>(createdUserRest, headers,
				HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<UserRest> viewUser(@PathVariable Integer id) {

		ViewResponseEvent<User> response = service
				.request(new ViewRequestEvent<User>(id));

		if (!response.isEntityFound()) {
			return new ResponseEntity<UserRest>(HttpStatus.NOT_FOUND);
		}

		UserRest userRest = UserRest.fromCore(response.getObject());

		return new ResponseEntity<UserRest>(userRest, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<UserRest> deleteUser(@PathVariable Integer id) {

		DeleteResponseEvent<User> response = service
				.request(new DeleteRequestEvent(id));

		if (!response.isEntityFound()) {
			return new ResponseEntity<UserRest>(HttpStatus.NOT_FOUND);
		}

		UserRest userRest = UserRest.fromCore(response.getObject());

		if (!response.isOperationCompleted()) {
			return new ResponseEntity<UserRest>(userRest, HttpStatus.FORBIDDEN);
		}

		return new ResponseEntity<UserRest>(userRest, HttpStatus.NO_CONTENT);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<UserRest> updateUser(@PathVariable Integer id,
			@RequestBody UserRest UserRest) {
		
		UpdateResponseEvent<User> response;
		try {
			response = service.request(new UpdateRequestEvent<User>(id,
					UserRest.toCore()));
		} catch (ConstraintViolationException exception) {
			Logger.getLogger(UserController.class.getSimpleName()).
			log(Level.ALL, exception.getMessage(), exception);
			return new ResponseEntity<UserRest>(HttpStatus.BAD_REQUEST);
		}

		return generateReturn(UserRest, response);
	}

	private ResponseEntity<UserRest> generateReturn(UserRest userRest,
			UpdateResponseEvent<User> response) {
		if (!response.isEntityFound()) {
			return new ResponseEntity<UserRest>(HttpStatus.NOT_FOUND);
		}

		UserRest updatedUserRest = UserRest.fromCore(response.getObject());

		if (!response.isOperationCompleted()) {
			return new ResponseEntity<UserRest>(userRest,
					HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<UserRest>(updatedUserRest, HttpStatus.OK);
	}

}
