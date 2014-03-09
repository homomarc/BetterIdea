package com.betteridea.connection;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.http.xml.atom.AtomContent;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.client.xml.XmlNamespaceDictionary;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;


public class Database {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			byte text = (byte)1;
			getRequest("http://space-labs.appspot.com/repo/2185003/ideas/api/idea.sjs");
			putRequest("http://space-labs.appspot.com/repo/2185003/ideas/api/idea.sjs", text);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	
    public static void getRequest(String reqUrl) throws IOException {
        GenericUrl url = new GenericUrl(reqUrl);
        HttpRequest request = HTTP_TRANSPORT.createRequestFactory().buildGetRequest(url);
        HttpResponse response = request.execute();
        System.out.println(response.getStatusCode());
        InputStream is = response.getContent();
        int ch;
        while ((ch = is.read()) != -1) {
            System.out.print((char) ch);
        }
        response.disconnect();
    }
	static HttpContent httpContent = new HttpContent() {
		
		@Override
		public void writeTo(OutputStream arg0) throws IOException {
			
		}
		
		@Override
		public boolean retrySupported() {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public String getType() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public long getLength() throws IOException {
			// TODO Auto-generated method stub
			return 0;
		}
	};
    static void putRequest(String reqUrl, Object data) throws IOException{
    	GenericUrl url1 = new GenericUrl(reqUrl); 
    	JsonHttpContent content = new JsonHttpContent(null, data);
    	HttpRequest request = HTTP_TRANSPORT.createRequestFactory().buildPutRequest(url1, content);
        String response = request.execute().parseAsString();
        System.out.println(response);
    }

    
}
