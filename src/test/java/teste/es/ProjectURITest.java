package teste.es;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
import br.edu.ufcg.embedded.projectmanager.rest.ProjectController;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProjectURITest {

	MockMvc mockMvc;

	@InjectMocks
	ProjectController controller;

	@Mock
	ProjectService service;

	private Project project = ProjectData.project1();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = standaloneSetup(controller).build();

		project.setId(1);

	}

	@SuppressWarnings("unchecked")
	@Test
	public void test1ListProjects() throws Exception {

		when(service.request(any(ListRequestEvent.class))).thenReturn(
				new ListResponseEvent<Project>(ProjectData.projects()));

		mockMvc.perform(
				MockMvcRequestBuilders.get("/projects").accept(
						MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(1))
				.andExpect(jsonPath("$[0].name").value("Scrum"))
				.andExpect(
						jsonPath("$[0].description").value("Project Details!"))
				.andExpect(jsonPath("$[1].id").value(2))
				.andExpect(jsonPath("$[1].name").value("XP"))
				.andExpect(jsonPath("$[1].description").value("Extreme!"))
				.andExpect(jsonPath("$[2].id").value(3))
				.andExpect(jsonPath("$[2].name").value("RUP"))
				.andExpect(jsonPath("$[2].description").value("Rational!"));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test2CreateProject() throws Exception {

		when(service.request(any(CreateRequestEvent.class))).thenReturn(
				new CreateResponseEvent<Project>(project));

		mockMvc.perform(
				MockMvcRequestBuilders.post("/projects")
						.content(ProjectData.projectJson1())
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("Scrum"))
				.andExpect(jsonPath("$.description").value("Project Details!"));
		;
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test3UpdateProject() throws Exception {

		when(service.request(any(UpdateRequestEvent.class))).thenReturn(
				new UpdateResponseEvent<Project>(project));

		String uri = "/projects/"
				+ project.getId();

		project.setFullName("Name Changed!");
		project.setFullDescription("Description Changed!");

		mockMvc.perform(
				MockMvcRequestBuilders.put(uri)
						.content(ProjectData.projectToJson(project))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("Name Changed!"))
				.andExpect(
						jsonPath("$.description").value("Description Changed!"));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test4ViewProject() throws Exception {

		when(service.request(any(ViewRequestEvent.class))).thenReturn(
				new ViewResponseEvent<Project>(project.getId(), project));

		String uri = "/projects/"
				+ project.getId();
		mockMvc.perform(
				MockMvcRequestBuilders.get(uri).accept(
						MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("Scrum"))
				.andExpect(jsonPath("$.description").value("Project Details!"));
	}

	@Test
	public void test5DeleteProject() throws Exception {

		when(service.request(any(DeleteRequestEvent.class))).thenReturn(
				new DeleteResponseEvent<Project>(project));

		String uri = "/projects/"
				+ project.getId();
		mockMvc.perform(
				MockMvcRequestBuilders.delete(uri).accept(
						MediaType.APPLICATION_JSON)).andExpect(
				status().isNoContent());
	}

}
