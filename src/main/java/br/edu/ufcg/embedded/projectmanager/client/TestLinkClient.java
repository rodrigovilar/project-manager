package br.edu.ufcg.embedded.projectmanager.client;

import java.net.MalformedURLException;
import java.net.URL;

import br.edu.ufcg.embedded.projectmanager.domain.Project;
import br.edu.ufcg.embedded.projectmanager.exception.EventException;
import br.edu.ufcg.embedded.projectmanager.exception.TestLinkException;
import br.eti.kinoshita.testlinkjavaapi.TestLinkAPI;
import br.eti.kinoshita.testlinkjavaapi.model.TestPlan;
import br.eti.kinoshita.testlinkjavaapi.model.TestProject;
import br.eti.kinoshita.testlinkjavaapi.util.TestLinkAPIException;

public class TestLinkClient extends ProjectClient{
	
	private TestLinkAPI api;
	private static TestLinkClient client;
	
	private TestLinkClient() throws EventException{
		try {
			api = new TestLinkAPI(new URL(Constants.URL_TESTLINK), Constants.TESTLINK_DEVKEY);
		} catch (TestLinkAPIException | MalformedURLException e) {
			throw new TestLinkException("Erro ao criar projeto TestLink: "+e.getMessage());
		}
	}
	
	public static TestLinkClient instance() throws EventException {
		if (client == null)
			client = new TestLinkClient();		
		return client;
	}
	
	public void createTestLinkProject(Project project) throws EventException {
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
				                    true //isPublic (precisa ser publico para que seja possivel criar o test plan)
				                    ); 
			
			System.out.println( "Test Project ID: [ " + testlinkProject.getId() + " ]." );	
			
			createTestPlan(testlinkProject);
		} catch( TestLinkAPIException e) {
			throw new TestLinkException("Erro ao criar projeto TestLink: " + e.getMessage());
		}
	}	
	
	private void createTestPlan(TestProject project) {		
		TestPlan testPlan = api.createTestPlan(
				"Complete", // Plan name
				project.getName(), //Project Name
				project.getNotes(),  //Description
				project.isActive(), //Is Active
				project.isPublic() //Is Public
				); 
		System.out.println( "Test Plan ID: [ " + testPlan.getId() + " ]." );	
	}
}
