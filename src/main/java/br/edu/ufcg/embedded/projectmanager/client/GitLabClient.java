package br.edu.ufcg.embedded.projectmanager.client;

import java.io.IOException;

import org.gitlab.api.GitlabAPI;
import org.gitlab.api.models.GitlabProject;

import br.edu.ufcg.embedded.projectmanager.domain.Project;
import br.edu.ufcg.embedded.projectmanager.exception.GitLabException;
import br.edu.ufcg.embedded.projectmanager.exception.ProjectException;

public class GitLabClient extends ProjectClient {
	
	private static GitLabClient client;	
	private GitlabAPI api;
	
	private GitLabClient() {
		api = GitlabAPI.connect(Constants.URL_GITLAB, Constants.GITLAB_DEVKEY);
	}
	
	public static GitLabClient instance() {
		if(client == null)
			client = new GitLabClient();
		return client;
	}	
	
	public void createGitLabProject(Project project) throws ProjectException {		
		try {			
			GitlabProject gitlabProject = api.createProject(
					project.getShortName(), // The name of the project
					null, //The Namespace for the new project, otherwise null indicates to use the GitLab default (user)
					project.getFullDescription(), //A description for the project, null otherwise
					true, //Whether Issues should be enabled, otherwise null indicates to use GitLab default
					true, //Whether The Wall should be enabled, otherwise null indicates to use GitLab default
					true, //Whether Merge Requests should be enabled, otherwise null indicates to use GitLab default
					true, //Whether a Wiki should be enabled, otherwise null indicates to use GitLab default
					null, //Whether Snippets should be enabled, otherwise null indicates to use GitLab default
					project.getProjectVisibility().equals("0")?true:false, //Whether the project is public or private,
					null, //The visibility level of the project, otherwise null indicates to use GitLab default 
					null //The Import URL for the project, otherwise null
					);
			
			System.out.println( "Test Project ID: [ " + gitlabProject.getId() + " ]." );	
			
		} catch (IOException e) {
			throw new GitLabException("Erro ao criar projeto GitLab: "+e.getMessage());
		}
		
	}
}