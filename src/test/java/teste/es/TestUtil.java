package teste.es;

import java.util.List;

import br.edu.ufcg.embedded.projectmanager.generic.GenericService;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtil {

	public static String toJson(Object object) {
		try {
			return new ObjectMapper().writeValueAsString(object);
		} catch (JsonProcessingException e) {
		}
		return "";
	}

	public static <T> T create(GenericService<T> service, T object) {
		CreateResponseEvent<T> response = service
				.request(new CreateRequestEvent<T>(object));
		return response.getObject();
	}

	public static <T> T update(GenericService<T> service, Integer id, T object) {
		UpdateResponseEvent<T> response = service
				.request(new UpdateRequestEvent<T>(id, object));
		return response.getObject();
	}

	public static <T> T delete(GenericService<T> service, Integer id) {
		DeleteResponseEvent<T> response = service
				.request(new DeleteRequestEvent(id));
		return response.getObject();
	}

	public static <T> List<T> list(GenericService<T> service) {
		ListResponseEvent<T> response = service
				.request(new ListRequestEvent<T>());
		return response.getObjects();
	}

	public static <T> T view(GenericService<T> service, Integer id) {
		ViewResponseEvent<T> response = service
				.request(new ViewRequestEvent<T>(id));
		return response.getObject();
	}

}
