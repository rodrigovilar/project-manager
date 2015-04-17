package br.edu.ufcg.embedded.projectmanager.generic;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.BeanUtils;

public class RestUtil {
	
	private RestUtil(){		
	}

	public static <F, T> T convert(F from, T to) {
		if (from != null && to != null) {
			BeanUtils.copyProperties(from, to);
		}
		return to;
	}
	
	public static List<Integer> getIDs(List<? extends Identifiable> objects) {
		List<Integer> ids = new LinkedList<Integer>();
		for (Identifiable object : objects) {
			ids.add(object.getId());
		}
		return ids;
	}
}
