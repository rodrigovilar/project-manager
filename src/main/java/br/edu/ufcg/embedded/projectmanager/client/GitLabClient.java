package br.edu.ufcg.embedded.projectmanager.client;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import br.edu.ufcg.embedded.projectmanager.domain.Project;
import br.edu.ufcg.embedded.projectmanager.exception.ProjectException;
import br.edu.ufcg.embedded.projectmanager.exception.TuleapException;

public class GitLabClient extends ProjectClient {
	
	public static void createGitLabProject(Project project) throws ProjectException {
		try {
			final String urlLogin = Constants.URL_GITLAB + "/users/sign_in";
			final String urlProject = Constants.URL_GITLAB + "/projects/new";
			final String urlCreateProject = Constants.URL_GITLAB + "/projects";
			final String user = "ci-asus";
			final String password = "ciasus2015";			
			
			final CloseableHttpClient httpClient = ClientSSLConfig.createHttpClient();
									
			/* Get token and cookie of page Login */
			HttpGet httpGetLogin = new HttpGet(urlLogin); 
			HttpResponse responseGetLogin = httpClient.execute(httpGetLogin);
			HttpEntity entityGetLogin =  responseGetLogin.getEntity();
			String cookiePreLogin = getCookie(responseGetLogin);
	        String tokenPreLogin = getToken(entityGetLogin)  ;     
	        EntityUtils.consume(entityGetLogin);
	        	        
			/* POST page Login */ 
			HttpPost httpPostLogin = new HttpPost(urlLogin);			
			httpPostLogin.addHeader("Content-Type", "application/x-www-form-urlencoded");
			httpPostLogin.addHeader("Cookie", cookiePreLogin);
			List<NameValuePair> dataPostLogin = new ArrayList<NameValuePair>(); 
			dataPostLogin.add(new BasicNameValuePair("authenticity_token", tokenPreLogin)); 
			dataPostLogin.add(new BasicNameValuePair("user[login]", user)); 
			dataPostLogin.add(new BasicNameValuePair("user[password]", password)); 
			dataPostLogin.add(new BasicNameValuePair("user[remember_me]", "0")); 
			httpPostLogin.setEntity(new UrlEncodedFormEntity(dataPostLogin, Consts.UTF_8)); 			
			HttpResponse responsePostLogin = httpClient.execute(httpPostLogin);				        
			String cookiePosLogin = getCookie(responsePostLogin);        
			EntityUtils.consume(responsePostLogin.getEntity());
			
			
			/* GET page Create Project (restricted page) and update token */ 
			HttpGet httpGetProject = new HttpGet(urlProject);
			httpGetProject.addHeader("Cookie", cookiePosLogin);
			HttpResponse responseGetToken = httpClient.execute(httpGetProject);	
			HttpEntity entityGetToken = responseGetToken.getEntity();						
			String tokenPosLogin = getToken(entityGetToken);  	
			EntityUtils.consume(entityGetToken);			
			
			HttpResponse responseGetCreateProject = httpClient.execute(httpGetProject);	
			HttpEntity entityGetCreateProject = responseGetCreateProject.getEntity();
			if(checkSuccess(entityGetCreateProject, "New Project")){	//Login successful	
				
				HttpPost httpPostCreateProject = new HttpPost(urlCreateProject);
				httpPostCreateProject.addHeader("Content-Type", "application/x-www-form-urlencoded");
				httpPostCreateProject.addHeader("Cookie", cookiePosLogin);			 		
				List<NameValuePair> dataPost = new ArrayList<NameValuePair>(); 
				dataPost.add(new BasicNameValuePair("authenticity_token", tokenPosLogin));
				dataPost.add(new BasicNameValuePair("project[path]", project.getShortName())); 
				dataPost.add(new BasicNameValuePair("project[description]", project.getFullDescription())); 
				dataPost.add(new BasicNameValuePair("project[visibility_level]", "0")); //repo private 
				httpPostCreateProject.setEntity(new UrlEncodedFormEntity(dataPost, Consts.UTF_8)); 		
				HttpResponse responsePostCreateProject = httpClient.execute(httpPostCreateProject); 
				HttpEntity entityPostCreateProject = responsePostCreateProject.getEntity();
				
				if(checkSuccess(entityPostCreateProject, project.getShortName())) {
					 System.out.println("PROJECT GITLAB CREATED"); // melhorar esse parte.
				} else {
					throw new TuleapException("Erro ao criar projeto GitLab, parametros invalidos");
				}				
			} else {
				throw new TuleapException("Erro ao criar projeto GitLab, falha no login");
			}
		  	EntityUtils.consume(entityGetCreateProject);			
		} catch (IOException | KeyManagementException | NoSuchAlgorithmException e) {
			throw new TuleapException("Erro ao criar projeto GitLab");
		} 		
	}	
	
	private static String getToken(HttpEntity entity) throws ParseException, IOException {
		String HTML = EntityUtils.toString(entity);
        Document doc = Jsoup.parse(HTML); 
        Elements elements = doc.select("input");
        for (Element element:elements) 
        	if (element.toString().contains("authenticity_token")) 
        		return element.toString().split("\"")[5];          
        return "";
	}
	
	private static String getCookie(HttpResponse response) {
		Header[] headers = response.getAllHeaders();
    	for (Header header : headers) 
    		if(header.getName().toString().equals("Set-Cookie"))
    			return header.getValue().split(";")[0];    	
    	return "";
	}	
}