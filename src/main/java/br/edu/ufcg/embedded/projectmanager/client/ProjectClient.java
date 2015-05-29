package br.edu.ufcg.embedded.projectmanager.client;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public abstract class ProjectClient {
		
	public static boolean checkSuccess(final HttpEntity entity, String string) throws IOException { 		 	 
		String HTML = EntityUtils.toString(entity);		
        Document doc = Jsoup.parse(HTML); 
        Elements elements = doc.getAllElements();
        for (Element element:elements) {
        	if (element.toString().contains(string)) 
        		return true;
        }
        return false;
	}	
}
