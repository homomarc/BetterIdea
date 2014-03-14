package com.betteridea.connection;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.InputStream;


public class Database {

	static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	static final JsonFactory JSON_FACTORY = new JacksonFactory();
	
	public static void databaseInterface(int userID, String table, String method, int var1, String var2, String var3, String var4) throws IOException{
		String reqUrl = null;
		JsonObject json= new JsonObject();
		if(table == "idea"){
			reqUrl = "http://space-labs.appspot.com/repo/2185003/ideas/api/idea.sjs";
			json.addProperty("Room", var1);
			//TODO: Properties
		}
		else if(table == "comment"){
			reqUrl = "http://space-labs.appspot.com/repo/2185003/ideas/api/comment.sjs";
			//TODO: Properties
		}
		else if(table == "credit"){
			reqUrl = "http://space-labs.appspot.com/repo/2185003/ideas/api/credit.sjs";
			//TODO: Properties
		}
		else{
			//TODO: Error-Message
		}
		if(method == "get"){
			getRequest(reqUrl);
		}
		else if(method == "put"){
			postRequest(reqUrl, json);
		}
		else if(method == "post"){
			postRequest(reqUrl, json);
		}
		else if(method == "delete"){
			postRequest(reqUrl, json);
		}
		else{
			//TODO: Error-Message
		}
	}


	// To get Data
    public static void getRequest(String reqUrl) throws IOException {
        GenericUrl url = new GenericUrl(reqUrl);
        HttpRequest request = HTTP_TRANSPORT.createRequestFactory().buildGetRequest(url);
        HttpResponse response = request.execute();
        System.out.println("GET: " + response.getStatusCode());
        InputStream is = response.getContent();
        int ch;
        while ((ch = is.read()) != -1) {
            System.out.print((char) ch);
        }
        response.disconnect();
    }
    // To update Data
    static void postRequest(String reqUrl, JsonObject json) throws IOException{
    	GenericUrl url1 = new GenericUrl(reqUrl); 
    	HttpRequest request = HTTP_TRANSPORT.createRequestFactory().buildPostRequest(url1, ByteArrayContent.fromString("application/json", json.toString()));
        String response = request.execute().parseAsString();
        System.out.println("POST: " + response);
    }   
    // To create new object
    static void putRequest(String reqUrl, JsonObject json) throws IOException{
    	GenericUrl url1 = new GenericUrl(reqUrl); 
    	HttpRequest request = HTTP_TRANSPORT.createRequestFactory().buildPutRequest(url1, ByteArrayContent.fromString("application/json", json.toString()));
        String response = request.execute().parseAsString();
        System.out.println("PUT: " + response);
    }  
    // To delete object
	static void deleteRequest(String reqUrl, JsonObject json) throws IOException{
    	GenericUrl url1 = new GenericUrl(reqUrl); 
    	HttpRequest request = HTTP_TRANSPORT.createRequestFactory().buildPostRequest(url1, ByteArrayContent.fromString("application/json", json.toString()));
        String response = request.execute().parseAsString();
        System.out.println("DELETE: " + response);
	}
}
