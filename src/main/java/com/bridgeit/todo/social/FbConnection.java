package com.bridgeit.todo.social;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Component
public class FbConnection {

	public static final String FB_APP_ID = "493599474327815";
	public static final String FB_APP_SECRET = "9f7095b18e5b923b70432e3d6868f7fc";
	public static final String REDIRECT_URI = "http://localhost:8011/ToDo/todohome";

	public String getFBAuthUrl() {
		System.out.println("in facebook connection");
		
		String fbLoginUrl = "";
		try {
			fbLoginUrl = "http://www.facebook.com/dialog/oauth?" + "client_id=" + FbConnection.FB_APP_ID
					+ "&redirect_uri=" + URLEncoder.encode(FbConnection.REDIRECT_URI, "UTF-8") + "&scope=email";
		} 
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return fbLoginUrl;
	}
	
	public String getAccessToken(String code) {

		String fbaccesstokenUrl = "";
		try {
			fbaccesstokenUrl = "https://graph.facebook.com/oauth/access_token?" + "client_id="+ FbConnection.FB_APP_ID + "&redirect_uri="
					+ URLEncoder.encode(FbConnection.REDIRECT_URI, "UTF-8") + "&client_secret=" + FB_APP_SECRET	+ "&code=" + code;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(fbaccesstokenUrl);
		Response response = target.request().accept(MediaType.APPLICATION_JSON).get();
		String fbToken = response.readEntity(String.class);
		ObjectMapper mapper = new ObjectMapper();
		String token = null;
		try {

			token = mapper.readValue(fbToken, String.class);

		}  catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("facebook token is :" + token);
		
		client.close();

		return token;
	}
}
