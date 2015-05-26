package br.edu.ufcg.embedded.projectmanager.client;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
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
import br.edu.ufcg.embedded.projectmanager.exception.GitLabException;
import br.edu.ufcg.embedded.projectmanager.exception.ProjectException;

public class GitLabClient extends ProjectClient {
	
	public static void createGitLabProject(Project project) throws ProjectException {
		try {
			final String urlLogin = Constants.URL_GITLAB + "/users/sign_in";
			final String user = "franclisgaldino";
			final String password = "mariag83";
			
			
			CloseableHttpClient httpClient = ClientSSLConfig.createHttpClient();
									
			/* Get token page */
			HttpGet httpGetProject = new HttpGet(urlLogin); 
			HttpResponse responseGetLogin = httpClient.execute(httpGetProject);			
	        HttpEntity entity = responseGetLogin.getEntity();	 
	        
	        String authenticity_token = "";
			String cookie = "";
			Header[] headers = responseGetLogin.getAllHeaders();
	    	for (Header header : headers) {
	    		if(header.getName().toString().equals("Set-Cookie")){
	    			cookie = header.getValue().split(";")[0];
	    			break;
	    		}    
	    	}	        
	        String HTML = EntityUtils.toString(entity);
	        Document doc = Jsoup.parse(HTML);
	        Elements elements = doc.select("input");
	        for (Element element:elements) {
	        	if (element.toString().contains("authenticity_token")) {
	        		authenticity_token = element.toString().split("\"")[5];
	        		break;
	        	}
	        }
	        EntityUtils.consume(entity);
	        

	    	System.out.println("Cookie:"+cookie);
	    	System.out.println("Token:"+authenticity_token);
	        
			/* POST page Login */ 
			HttpPost httpPostLogin = new HttpPost(urlLogin); 		
			List<NameValuePair> dataPostLogin = new ArrayList<NameValuePair>(); 
			httpPostLogin.addHeader("Content-Type", "application/x-www-form-urlencoded");
			httpPostLogin.addHeader("_gitlab_session", cookie.split("=")[1]);
			dataPostLogin.add(new BasicNameValuePair("user[login]", user)); 
			dataPostLogin.add(new BasicNameValuePair("user[password]", password)); 
			dataPostLogin.add(new BasicNameValuePair("user[remember_me]", "0")); 
			dataPostLogin.add(new BasicNameValuePair("authenticity_token", authenticity_token)); 
			httpPostLogin.setEntity(new UrlEncodedFormEntity(dataPostLogin, Consts.UTF_8)); 			
			HttpResponse responsePostLogin = httpClient.execute(httpPostLogin); 
			
			if (checkSuccess(responsePostLogin,"Signed in successfully")) {
				 System.out.println("LOGIN SUCESSFULL");
			} else{
				System.out.println("LOGIN FAIL");
			}
			EntityUtils.consume(responsePostLogin.getEntity()); 
		} catch (Exception e){
			throw new GitLabException("Erro ao criar projeto GitLab");
		} 	
	}	
}
