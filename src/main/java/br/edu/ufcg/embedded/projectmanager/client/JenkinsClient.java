package br.edu.ufcg.embedded.projectmanager.client;

import java.io.File;
import java.io.FileInputStream;
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
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import br.edu.ufcg.embedded.projectmanager.domain.Project;
import br.edu.ufcg.embedded.projectmanager.exception.EventException;
import br.edu.ufcg.embedded.projectmanager.exception.JenkinsException;

public class JenkinsClient extends ProjectClient {
	
	public static void createJenkinsProject(Project project) throws EventException {
		createJenkinsView(project.getFullName());
		
		createJenkinsJob(project, "DEV");
		createJenkinsJob(project, "INT");
		createJenkinsJob(project, "UAT");
	}
	
	private static void createJenkinsView(String viewName) throws JenkinsException {
        try {
        	String cookie = checkViewName(viewName);
        	
        	final String urlCreateView = Constants.URL_JENKINS + "/createView";
        	
        	CloseableHttpClient httpClient = ClientSSLConfig.createHttpClient();
	        
	        HttpPost httpPostCreateView = new HttpPost(urlCreateView);
			httpPostCreateView.addHeader("Content-Type", "application/x-www-form-urlencoded");
			httpPostCreateView.addHeader("Cookie", cookie);

			List<NameValuePair> dataPost = new ArrayList<NameValuePair>();
			dataPost.add(new BasicNameValuePair("name", viewName));
			dataPost.add(new BasicNameValuePair("mode", "hudson.model.ListView"));
			String json = "{\"name\": \"" + viewName + "\", \"mode\": \"hudson.model.ListView\"}";
			dataPost.add(new BasicNameValuePair("json", json));
			
			httpPostCreateView.setEntity(new UrlEncodedFormEntity(dataPost, Consts.UTF_8));
			httpClient.execute(httpPostCreateView);

		} catch (IOException | KeyManagementException | NoSuchAlgorithmException e) {
			throw new JenkinsException("Erro ao criar view Jenkins 3");
		}
	}
	
	private static String checkViewName(String viewName) throws JenkinsException {
		try {
			final String urlCheckViewName = Constants.URL_JENKINS + "/checkViewName?value=" + viewName;
			
			CloseableHttpClient httpClient = ClientSSLConfig.createHttpClient();
			
			// Get cookie of page Create View
			HttpGet httpGetCheckViewName = new HttpGet(urlCheckViewName);
			HttpResponse responseGetCheckViewName = httpClient.execute(httpGetCheckViewName);
			HttpEntity entityGetCheckViewName =  responseGetCheckViewName.getEntity();
			String cookieJenkins = getCookie(responseGetCheckViewName);
	        
	        String HTML = EntityUtils.toString(entityGetCheckViewName);
	        Document doc = Jsoup.parse(HTML);
	        Elements elements = doc.select("div");
	        for (Element element:elements) {
	        	if (element.toString().contains(viewName)) {
	        		throw new JenkinsException("Erro ao criar view Jenkins 1");
	        	}
	        }

	        EntityUtils.consume(entityGetCheckViewName);
	        
	        return cookieJenkins;
	        
		} catch (IOException | KeyManagementException | NoSuchAlgorithmException e) {
			throw new JenkinsException("Erro ao criar view Jenkins 2");
		}
	}
	
	private static void createJenkinsJob(Project project, String sufix) throws JenkinsException {
		try {
			final String urlCreateItem = Constants.URL_JENKINS + "/view/" + project.getFullName() + "/createItem?name="
					+ project.getFullName().toLowerCase() + "-" + sufix;
			
			CloseableHttpClient httpClient = ClientSSLConfig.createHttpClient();
	        
	        HttpPost httpPostCreateItem = new HttpPost(urlCreateItem);
			httpPostCreateItem.addHeader("Content-Type", "text/xml");
			
			File file = null;
			
			if (project.getType().equals("android") && (sufix.equals("DEV") || sufix.equals("UAT"))) {
				file = new File("src/main/config/android/config-dev.xml");
			} else if (project.getType().equals("android") && sufix.equals("INT")) {
				file = new File("src/main/config/android/config-int.xml");
			}
			
			int ch;
			StringBuffer sb = new StringBuffer("");
			FileInputStream fis = new FileInputStream(file);
			
			while((ch = fis.read()) != -1) {
				sb.append((char) ch);
			}
			
			fis.close();
							
			StringEntity entity = new StringEntity(sb.toString());
			entity.setContentType("text/xml");
			httpPostCreateItem.setEntity(entity);

			httpClient.execute(httpPostCreateItem);
			
		} catch(IOException | KeyManagementException | NoSuchAlgorithmException e) {
			throw new JenkinsException("Erro ao criar view Jenkins 4 " + sufix);
		}
	}
	
	private static String getCookie(HttpResponse response) {
		Header[] headers = response.getAllHeaders();
    	for (Header header : headers) {
    		if(header.getName().toString().equals("Set-Cookie")) {
    			return header.getValue().split(";")[0];
    		}
    	}
    	return "";
	}
}