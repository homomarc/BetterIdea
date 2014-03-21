package com.betteridea.connection;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.gson.JsonObject;
import java.io.IOException;
import org.json.*;

public class Database{

	static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	
	// To get object
    public static String getRequest(String reqUrl) throws IOException, JSONException {
        GenericUrl url = new GenericUrl(reqUrl);
        HttpRequest request = HTTP_TRANSPORT.createRequestFactory().buildGetRequest(url);
        String response = request.execute().parseAsString();
        System.out.println(response);
        return response;
    }
    // To update object
    static String postRequest(String reqUrl, JsonObject json) throws IOException{
    	GenericUrl url1 = new GenericUrl(reqUrl); 
    	HttpRequest request = HTTP_TRANSPORT.createRequestFactory().buildPostRequest(url1, ByteArrayContent.fromString("application/json", json.toString()));
        String response = request.execute().parseAsString();
        System.out.println("POST: " + response);
        if(response == "true"){
        	String wahr = "true";
        	return wahr;
        }
        String falsch = "false";
        return falsch;
    }   
    // To create new object
    static String putRequest(String reqUrl, JsonObject json) throws IOException{
    	GenericUrl url1 = new GenericUrl(reqUrl); 
    	HttpRequest request = HTTP_TRANSPORT.createRequestFactory().buildPutRequest(url1, ByteArrayContent.fromString("application/json", json.toString()));
        String response = request.execute().parseAsString();
        System.out.println("PUT: " + response);
        if(response == "true"){
        	String wahr = "true";
        	return wahr;
        }
        String falsch = "false";
        return falsch;
    }  
    // To delete object
	static String deleteRequest(String reqUrl) throws IOException{
    	GenericUrl url1 = new GenericUrl(reqUrl); 
    	HttpRequest request = HTTP_TRANSPORT.createRequestFactory().buildDeleteRequest(url1);
        String response = request.execute().parseAsString();
        System.out.println("DELETE: " + response);
        if(response == "true"){
        	String wahr = "true";
        	return wahr;
        }
        String falsch = "false";
        return falsch;
	}
}
