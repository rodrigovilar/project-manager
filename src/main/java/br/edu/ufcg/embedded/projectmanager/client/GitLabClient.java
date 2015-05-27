package br.edu.ufcg.embedded.projectmanager.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import br.edu.ufcg.embedded.projectmanager.domain.Project;
import br.edu.ufcg.embedded.projectmanager.exception.GitLabException;
import br.edu.ufcg.embedded.projectmanager.exception.ProjectException;
import br.edu.ufcg.embedded.projectmanager.exception.TuleapException;

public class GitLabClient extends ProjectClient {
	
	public static void createGitLabProject(Project project) throws ProjectException {
		try {
			final String urlLogin = Constants.URL_GITLAB + "/users/sign_in";
			final String urlCreateProject = Constants.URL_GITLAB + "/projects/new";
			final String user = "franclisgaldino";
			final String password = "mariag83";			
			
			final CloseableHttpClient httpClient = ClientSSLConfig.createHttpClient();
									
			/* Get token page Login */
			HttpGet httpGetLogin = new HttpGet(urlLogin); 
			HttpResponse responseGetLogin = httpClient.execute(httpGetLogin);	
	        HttpEntity entity = responseGetLogin.getEntity();	
	        	        
//			String cookie = "";
//			Header[] headers = responseGetLogin.getAllHeaders();
//	    	for (Header header : headers) {
//	    		if(header.getName().toString().equals("Set-Cookie")){
//	    			cookie = header.getValue().split(";")[0];
//	    			break;
//	    		}    
//	    	}	
	    	String authenticity_token = "";
	        String HTML = EntityUtils.toString(entity);
	        Document doc = Jsoup.parse(HTML);
	        Elements elements = doc.getAllElements();
	        for (Element element:elements) {
	        	System.out.println("Element: "+element.toString());
	        	if (element.toString().contains("authenticity_token")) {
	        		authenticity_token = element.toString().split("\"")[5];
	        		break;
	        	}
	        }	 
	       Elements elements2 = doc.select("input");
	        for (Element element:elements2) {
	        	if (element.toString().contains("authenticity_token")) {
	        		authenticity_token = element.toString().split("\"")[5];
	        		break;
	        	}
	        }	        
	        EntityUtils.consume(entity);
	    	System.out.println(">>>>Token: "+authenticity_token);
	        
			/* POST page Login */ 
			HttpPost httpPostLogin = new HttpPost(urlLogin); 
			httpPostLogin.addHeader("Content-Type", "application/x-www-form-urlencoded");
			List<NameValuePair> dataPostLogin = new ArrayList<NameValuePair>(); 
			dataPostLogin.add(new BasicNameValuePair("utf8","✓"));
			dataPostLogin.add(new BasicNameValuePair("authenticity_token", authenticity_token)); 
			dataPostLogin.add(new BasicNameValuePair("user[login]", user)); 
			dataPostLogin.add(new BasicNameValuePair("user[password]", password)); 
			dataPostLogin.add(new BasicNameValuePair("user[remember_me]", "0")); 
			httpPostLogin.setEntity(new UrlEncodedFormEntity(dataPostLogin, Consts.UTF_8)); 			
			HttpResponse responsePostLogin = httpClient.execute(httpPostLogin); 	
			EntityUtils.consume(responsePostLogin.getEntity());
			
			/* GET page Create Project (restricted page) */ 
			HttpGet httpGetProject = new HttpGet(urlCreateProject); 
			HttpResponse responseGetCreateProject = httpClient.execute(httpGetProject);			
			if(checkSuccess(responseGetCreateProject, "New Project")){
				System.out.println("LOGIN SUCESSFULL");
			} else{
				throw new TuleapException("Erro ao criar projeto Tuleap, falha no login");
			}
			EntityUtils.consume(responseGetCreateProject.getEntity());
		} catch (Exception e){
			throw new GitLabException("Erro ao criar projeto GitLab");
		} 	
	}	
}
