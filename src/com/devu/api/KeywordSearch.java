package com.devu.api;
import java.awt.List;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class KeywordSearch {

	
	public static ArrayList<String> getKeywordsForText(String body) {
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
			String url = "http://api.cortical.io/rest/text/keywords?retina_name=en_associative";
            HttpPost request = new HttpPost(url);
            StringEntity params = new StringEntity(body);
            request.addHeader("content-type", "application/json");
            request.addHeader("api-key","c15c7da0-43e7-11e7-b22d-93a4ae922ff1");
            request.setEntity(params);
            HttpResponse result = httpClient.execute(request);
            //convert array to usable String
            String json = EntityUtils.toString(result.getEntity(), "UTF-8");
            
            //convert String back into an array of words
            json = json.replaceAll("\"", "");
            json = json.replace("[", "");
            json = json.replace("]", "");
            String[] keys = json.split(",");
            ArrayList<String> newKeys = new ArrayList<String>();
            
            
            //deal with case inconsistencies(e.g. uppercase lowercase) and make keywords exactly as they appear in the paragraph
            for (String key: keys){
            	int c = body.toLowerCase().indexOf(key);
            	key = body.substring(c, c+key.length());
            	newKeys.add(key);
            }
            return newKeys;

        } catch (IOException e) {
        }
        return null;
		
	}
	public static void main(String[] args) throws Exception{
				
		String paragraph = ("");

		ArrayList<String> text = new ArrayList<String>(); 
		text = getKeywordsForText(paragraph);
		//text += "hi";

		for (String key: text){
			System.out.println(key);
		}
		
		

	}

}
