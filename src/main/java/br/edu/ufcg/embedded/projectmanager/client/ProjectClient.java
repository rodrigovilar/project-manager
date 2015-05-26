package br.edu.ufcg.embedded.projectmanager.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;

public class ProjectClient {
	
	 public static boolean checkSuccess(final HttpResponse response, String string) throws IOException { 
		final BufferedReader reader = new BufferedReader(new InputStreamReader( response.getEntity().getContent())); 
		String line; 
		boolean found = false; 
		while ((line = reader.readLine()) != null) { 			
			if(line.contains(string)) {
				return !found; 
			} 
		} 
		return found; 
	}	
}
