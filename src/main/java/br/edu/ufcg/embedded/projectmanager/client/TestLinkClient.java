package br.edu.ufcg.embedded.projectmanager.client;

import java.net.MalformedURLException;
import java.net.URL;

import br.edu.ufcg.embedded.projectmanager.domain.Project;
import br.edu.ufcg.embedded.projectmanager.exception.ProjectException;
import br.edu.ufcg.embedded.projectmanager.exception.TestLinkException;
import br.eti.kinoshita.testlinkjavaapi.TestLinkAPI;
import br.eti.kinoshita.testlinkjavaapi.model.TestPlan;
import br.eti.kinoshita.testlinkjavaapi.model.TestProject;
import br.eti.kinoshita.testlinkjavaapi.util.TestLinkAPIException;

public class TestLinkClient extends ProjectClient{
	
	private TestLinkAPI api;
	private static TestLinkClient client;
	
	private TestLinkClient() throws ProjectException{
		try {
			api = new TestLinkAPI(new URL(Constants.URL_TESTLINK), Constants.TESTLINK_DEVKEY);
		} catch (TestLinkAPIException | MalformedURLException e) {
			throw new TestLinkException("Erro ao criar projeto TestLink: "+e.getMessage());
		}
	}
	
	public static TestLinkClient instance() throws ProjectException {
		if (client == null)
			client = new TestLinkClient();		
		return client;
	}
	
	public void createTestLinkProject(Project project) throws ProjectException {
		try{					
			TestProject testlinkProject = api.createTestProject(
				                    project.getFullName(), //testProjectName
				                    project.getShortName(), //testProjectPrefix
				                    project.getFullDescription(), //description
				                    true, //enableRequirements
				                    true, //enableTestPriority
				                    true, //enableAutomation
				                    false, //enableInventory
				                    true, //isActive
				                    !project.getProjectVisibility().equals("0") //isPublic
				                    ); 
			
			System.out.println( "Test Project ID: [ " + testlinkProject.getId() + " ]." );	
			createTestPlan(project);
		} catch( TestLinkAPIException e) {
			throw new TestLinkException("Erro ao criar projeto TestLink: "+e.getMessage());
		}
	}
		
	private  void createTestPlan(Project project) {
		TestPlan testPlan = api.createTestPlan(
				"Complete", //Plan Name
				project.getFullName(), // Project Name
				project.getFullDescription(), //Description
				true, //Is Active
				!project.getProjectVisibility().equals("0") //Is Public
				);
		System.out.println( "Test Plan ID: [ " + testPlan.getId() + " ]." );		
	}
	
	
	
}
