package br.edu.ufcg.embedded.projectmanager.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import br.edu.ufcg.embedded.projectmanager.domain.Project;
import br.edu.ufcg.embedded.projectmanager.exception.ProjectException;
import br.edu.ufcg.embedded.projectmanager.exception.TuleapException;

public class TuleapClient {
	
	public static void createTuleapProject(Project project) throws ProjectException {
		try {
			final String urlLogin = Constants.URL_TULEAP + "/account/login.php";
			final String urlCreateProject = Constants.URL_TULEAP + "/project/register.php";
			final String user = "ci-asus";
			final String password = "ciasus2015";
					
			/* POST page Login */ 
			CloseableHttpClient httpClient = ClientSSLConfig.createHttpClient();
			HttpPost httpPostLogin = new HttpPost(urlLogin); 		
			List<NameValuePair> dataPostLogin = new ArrayList<NameValuePair>(); 
			httpPostLogin.addHeader("Content-Type", "application/x-www-form-urlencoded");
			dataPostLogin.add(new BasicNameValuePair("pv", "0")); 
			dataPostLogin.add(new BasicNameValuePair("form_loginname", user)); 
			dataPostLogin.add(new BasicNameValuePair("form_pw", password));  
			dataPostLogin.add(new BasicNameValuePair("stay_in_ssl", "1")); 
			dataPostLogin.add(new BasicNameValuePair("login", "Login")); 
			httpPostLogin.setEntity(new UrlEncodedFormEntity(dataPostLogin, Consts.UTF_8)); 		
			HttpResponse responsePostLogin = httpClient.execute(httpPostLogin); 
			EntityUtils.consume(responsePostLogin.getEntity()); 
			
			/* GET page Create Project */ 
			HttpGet httpGetProject = new HttpGet(urlCreateProject); 
			HttpResponse responseGetCreateProject = httpClient.execute(httpGetProject);			
			if(checkSuccess(responseGetCreateProject, "Project Creation request")){
				EntityUtils.consume(responseGetCreateProject.getEntity());
				
				/* POST page Create Project */
				HttpPost httpPostCreateProject = new HttpPost(urlCreateProject); 		
				List<NameValuePair> dataPost = new ArrayList<NameValuePair>(); 
				httpPostCreateProject.addHeader("Content-Type", "application/x-www-form-urlencoded");
				dataPost.add(new BasicNameValuePair("onestep", "true")); 
				dataPost.add(new BasicNameValuePair("form_full_name", project.getFullName())); 
				dataPost.add(new BasicNameValuePair("form_unix_name", project.getShortName()));  
				dataPost.add(new BasicNameValuePair("form_short_description", project.getShortDescription())); 
				dataPost.add(new BasicNameValuePair("form_101", project.getFullDescription())); 
				dataPost.add(new BasicNameValuePair("is_public", "0")); 
				dataPost.add(new BasicNameValuePair("form_license", "xrx")); 
				dataPost.add(new BasicNameValuePair("built_from_template", "100"));
				dataPost.add(new BasicNameValuePair("form_terms_of_services_approval", "approved")); 
				dataPost.add(new BasicNameValuePair("create_project", "Create the project")); 
				httpPostCreateProject.setEntity(new UrlEncodedFormEntity(dataPost, Consts.UTF_8)); 		
				HttpResponse responsePostCreateProject = httpClient.execute(httpPostCreateProject); 
				if(checkSuccess(responsePostCreateProject, "Registration completed")) {
					 System.out.println("PROJECT TULEAP CREATED"); // melhorar esse parte.
				} else {				
					throw new TuleapException("Erro ao criar projeto Tuleap, parametros invalidos");
				}
				EntityUtils.consume(responsePostCreateProject.getEntity()); 
			} else {
				throw new TuleapException("Erro ao criar projeto Tuleap, falha no login");
			}			
		} catch (Exception e){
			throw new TuleapException("Erro ao criar projeto Tuleap");
		} 
	}
		
	private static boolean checkSuccess(final HttpResponse response, String string) throws IOException { 
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
