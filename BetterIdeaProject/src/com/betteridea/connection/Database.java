package com.betteridea.connection;

/**
 * Author: 		Better Idea
 * Description:	Database Connectionklasse ist die zentrale Klasse um HTTP-Anfragen an den Server zu stellen.
 * 				Dabei wird eine Methode implementiert, die Umlaute in gültigen HTML-Code konvertiert.
 * 
 * TODOS:		keine
 * 
 */

import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import java.io.IOException;
import org.json.*;
import org.jsoup.Jsoup;

public class Database{
	static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	
	// GET object
    public static String getRequest(String reqUrl) throws IOException, JSONException {
        GenericUrl url = new GenericUrl(reqUrl);
        HttpRequest request = HTTP_TRANSPORT.createRequestFactory().buildGetRequest(url);
        String response = request.execute().parseAsString();
        System.out.println("GET: " + response);
        String resp = Jsoup.parse(response).text();
        return resp;
    }
    // UPDATE object
    public static String postRequest(String reqUrl, JSONObject json) throws IOException{
    	GenericUrl url1 = new GenericUrl(reqUrl);
    	String text = json.toString();
    	String jsn = stringToHtml(text);
    	HttpRequest request = HTTP_TRANSPORT.createRequestFactory().buildPostRequest(url1, ByteArrayContent.fromString("application/json", jsn));
        String response = request.execute().parseAsString();
        System.out.println("POST: " + response);
        return response;
    }   
    // CREATE new object
    public static String putRequest(String reqUrl, JSONObject json) throws IOException{
    	GenericUrl url1 = new GenericUrl(reqUrl); 
    	String text = json.toString();
    	String jsn = stringToHtml(text);
    	HttpRequest request = HTTP_TRANSPORT.createRequestFactory().buildPutRequest(url1, ByteArrayContent.fromString("application/json", jsn));
        String response = request.execute().parseAsString();
        System.out.println("PUT: " + response);
        return response;
    }  
    // DELETE object
    public static String deleteRequest(String reqUrl) throws IOException{
    	GenericUrl url1 = new GenericUrl(reqUrl); 
    	HttpRequest request = HTTP_TRANSPORT.createRequestFactory().buildDeleteRequest(url1);
        String response = request.execute().parseAsString();
        System.out.println("DELETE: " + response);
        return response;
	}
    
    //Umlautbehandlung
	static String stringToHtml(String input){
		int length = input.length();
		String output = "";
		for(int i=0; i<length; i++){
			char c = input.charAt(i);
			if(c == 'ä'){
				output += "&auml;";
			}else if(c == 'ö'){
				output += "&ouml;";
			}else if(c == 'ü'){
				output += "&uuml;";
			}else if(c == 'Ä'){
				output += "&Auml;";
			}else if(c == 'Ö'){
				output += "&Öuml;";
			}else if(c == 'Ü'){
				output += "&Uuml;";
			}else{
				output += c;
			}
		}
		return output;
	}
}