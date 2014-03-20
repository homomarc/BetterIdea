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
import org.json.*;

public class Database{

	static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	static final JsonFactory JSON_FACTORY = new JacksonFactory();
	/**
	 * @param args
	 * @throws IOException 
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 * @throws JSONException 
	 */
	public static String ideaInterface(String method, String ideaID, String authorID, String text, String desc, String archived, int openComments) throws IOException, JSONException{
		String reqUrl = null;
		String reqUrl1 = null;
		String delID = null;
		int postID = 0;
		String date1 = null;
		reqUrl = "http://space-labs.appspot.com/repo/2185003/ideas/api/idea.sjs";
		reqUrl1 = "http://space-labs.appspot.com/repo/2185003/ideas/api/ideaDel.sjs";
		
		JsonObject json= new JsonObject();
		if(method == "put"){
			java.util.Date now = new java.util.Date();
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
			String date = sdf.format(now);
			String responseID = getNextIdeaID();
			json.addProperty("ideaID", responseID);
			json.addProperty("authorID", authorID);
			json.addProperty("text", text);
			json.addProperty("date", date);
			json.addProperty("updated", "-");
			json.addProperty("description", desc);
			json.addProperty("archived", archived);
			json.addProperty("openComments", openComments);
		}
		if(method == "post"){
			
			java.util.Date now = new java.util.Date();
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
			String date = sdf.format(now);
			json.addProperty("ideaID", ideaID);
			json.addProperty("authorID", authorID);
			json.addProperty("text", text);
			json.addProperty("updated", date);
			json.addProperty("description", desc);
			json.addProperty("archived", archived);
			json.addProperty("openComments", openComments);
		}
		if(method == "get"){
			if(ideaID != null){
				JSONArray arr = getRequest(reqUrl, "ideaID", ideaID);
				if(authorID == "yes"){
					String obj = arr.getJSONObject(0).getString("authorID");
					return obj;
				}
				else if(text == "yes"){
					String obj = arr.getJSONObject(0).getString("text");
					return obj;
				}
				else if(desc == "yes"){
					String obj = arr.getJSONObject(0).getString("description");
					return obj;
				}
				else if(archived == "yes"){
					String obj = arr.getJSONObject(0).getString("archived");
					return obj;
				}
				else if(openComments == 1){
					String obj = arr.getJSONObject(0).getString("openComments");
					return obj;
				}else{
					JSONObject obj = arr.getJSONObject(0);
					return obj.toString();
				}
			}
			else if(authorID != null){
				JSONArray arr = getRequest(reqUrl, "authorID", authorID);
				return arr.toString();
			}
			else{
				JSONArray arr = getRequest(reqUrl, null, null);
				return arr.toString();
			}
		}
		else if(method == "put"){
			putRequest(reqUrl, json);
		}
		else if(method == "post"){
			JSONArray obj = getRequest(reqUrl, "ideaID", ideaID);
			postID = obj.getJSONObject(0).getInt("id");
			date1 = obj.getJSONObject(0).getString("date");
			json.addProperty("id", postID);
			json.addProperty("date", date1);
			postRequest(reqUrl, json);
		}
		else if(method == "delete"){
			JSONArray obj = getRequest(reqUrl, "ideaID", ideaID);
			delID = obj.getJSONObject(0).getString("id");
			deleteRequest(reqUrl1, delID);
		}
		else{
			//TODO: Error-Message
		}
		return null;
	}

	public static String getNextIdeaID() throws IOException, JSONException{
		String reqUrl = "http://space-labs.appspot.com/repo/2185003/ideas/api/idea.sjs";
		JSONArray arr = getRequest(reqUrl, null, null);
    	Boolean nextObject = true;
    	int objNumber = 0;
    	int value = 0;
    	int value1 = 0;
    	int compare = 0;
    	while(nextObject == true){
    		try {
    			value1 = arr.getJSONObject(objNumber).getInt("ideaID");
    			if(value1 > compare){
    				value = value1;
    			}
    			compare = value;
				objNumber++;
			} catch (JSONException e) {
				nextObject = false;
			}
    	}
    	value++;
    	String ret = String.valueOf(value);
    	return ret;
	}

	// To get object
    public static JSONArray getRequest(String reqUrl, String key, String content) throws IOException, JSONException {
        GenericUrl url = new GenericUrl(reqUrl);
        HttpRequest request = HTTP_TRANSPORT.createRequestFactory().buildGetRequest(url);
        HttpResponse response = request.execute();
        System.out.println("GET: " + response.getStatusCode());
        InputStream is = response.getContent();
        int ch;
        String result = "";
        while ((ch = is.read()) != -1) {
            result += (char)ch; 
        }
        response.disconnect();
        System.out.println(result);
    	JSONArray arr = new JSONArray(result);
        if(key != null && content != null){
            System.out.println("Search: ");
        	Boolean nextObject = true;
        	int objNumber = 0;
        	String value = null;
        	JSONArray jArr = new JSONArray();
        	while(nextObject == true){
        		try {
        			value = arr.getJSONObject(objNumber).getString(key);
						if(value.matches(content)){
							System.out.print("Found match: ");
							System.out.println(value);
							jArr.put(arr.getJSONObject(objNumber));
						}
						objNumber++;
				} catch (JSONException e) {
					nextObject = false;
				}
        	}
        return jArr;
        }else{
        	return arr;
        }
    }
    // To update object
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
	static void deleteRequest(String reqUrl, String json) throws IOException{
    	GenericUrl url1 = new GenericUrl(reqUrl); 
    	HttpRequest request = HTTP_TRANSPORT.createRequestFactory().buildPostRequest(url1, ByteArrayContent.fromString("application/json", json));
        String response = request.execute().parseAsString();
        System.out.println("DELETE: " + response);
	}
}
