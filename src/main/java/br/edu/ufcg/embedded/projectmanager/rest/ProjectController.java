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

import br.edu.ufcg.embedded.projectmanager.domain.Project;
import br.edu.ufcg.embedded.projectmanager.domain.ProjectService;
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
@RequestMapping("/projects")
public class ProjectController {

	@Autowired
	private ProjectService service;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<ProjectRest> listProjects() {

		ListResponseEvent<Project> response = service
				.request(new ListRequestEvent<Project>());

		List<ProjectRest> projects = new ArrayList<ProjectRest>();

		for (Project project : response.getObjects()) {
			projects.add(ProjectRest.fromCore(project));			
		}

		return projects;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<ProjectRest> projectProject(
			@RequestBody ProjectRest projectRest, UriComponentsBuilder builder) {
		CreateRequestEvent<Project> request = new CreateRequestEvent<Project>(
				projectRest.toCore());
		
		CreateResponseEvent<Project> response;

		try {
			response = service.request(request);
		} catch (ConstraintViolationException exception) {
			Logger.getLogger(ProjectController.class.getSimpleName()).
			log(Level.ALL, exception.getMessage(), exception);
			return new ResponseEntity<ProjectRest>(HttpStatus.BAD_REQUEST);
		}

		ProjectRest createdProjectRest = ProjectRest.fromCore(response.getObject());			

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/project/id")
				.buildAndExpand(createdProjectRest.getId().toString()).toUri());

		return new ResponseEntity<ProjectRest>(createdProjectRest, headers,
				HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<ProjectRest> viewProject(@PathVariable Integer id) {

		ViewResponseEvent<Project> response = service
				.request(new ViewRequestEvent<Project>(id));

		if (!response.isEntityFound()) {
			return new ResponseEntity<ProjectRest>(HttpStatus.NOT_FOUND);
		}

		ProjectRest projectRest = ProjectRest.fromCore(response.getObject());

		return new ResponseEntity<ProjectRest>(projectRest, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<ProjectRest> deleteProject(@PathVariable Integer id) {

		DeleteResponseEvent<Project> response = service
				.request(new DeleteRequestEvent(id));

		if (!response.isEntityFound()) {
			return new ResponseEntity<ProjectRest>(HttpStatus.NOT_FOUND);
		}

		ProjectRest projectRest = ProjectRest.fromCore(response.getObject());

		if (!response.isOperationCompleted()) {
			return new ResponseEntity<ProjectRest>(projectRest, HttpStatus.FORBIDDEN);
		}

		return new ResponseEntity<ProjectRest>(projectRest, HttpStatus.NO_CONTENT);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<ProjectRest> updateProject(@PathVariable Integer id,
			@RequestBody ProjectRest projectRest) {
		
		UpdateResponseEvent<Project> response;
		try {
			response = service.request(new UpdateRequestEvent<Project>(id,
					projectRest.toCore()));
		} catch (ConstraintViolationException exception) {
			Logger.getLogger(ProjectController.class.getSimpleName()).
			log(Level.ALL, exception.getMessage(), exception);
			return new ResponseEntity<ProjectRest>(HttpStatus.BAD_REQUEST);
		}

		return generateReturn(projectRest, response);
	}

	private ResponseEntity<ProjectRest> generateReturn(ProjectRest projectRest,
			UpdateResponseEvent<Project> response) {
		if (!response.isEntityFound()) {
			return new ResponseEntity<ProjectRest>(HttpStatus.NOT_FOUND);
		}

		ProjectRest updatedProjectRest = ProjectRest.fromCore(response.getObject());

		if (!response.isOperationCompleted()) {
			return new ResponseEntity<ProjectRest>(projectRest,
					HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<ProjectRest>(updatedProjectRest, HttpStatus.OK);
	}
}
