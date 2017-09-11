package com.bridgeit.todo.social;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class GmailLogin {
	
	public static final String App_Id = "1024956214628-kip3430tppne3bh7mgvrjt686frlfb1r.apps.googleusercontent.com";
	public static final String Secret_Id = "Ob4wKrc05h7qVWL5vnkrobAP";
	public static final String Redirect_URI = "http://localhost:8011/ToDo/gmail_login";
	
	public String getGoogleAuthURL(String unid) {
		
		String googleLoginURL = "";
		try{
			googleLoginURL = "https://accounts.google.com/o/oauth2/auth?client_id="+App_Id+"&redirect_uri="+URLEncoder.encode(Redirect_URI,"UTF-8")+
					"&state="+unid+"&response_type=code&scope=profile email&approval_prompt=force&access_type=offline";
			
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return googleLoginURL;
	}
	
	public String getAccessToken(String authCode) throws UnsupportedEncodingException{
		
		String accessTokenURL = "https://accounts.google.com/o/oauth2/token";
		
		ResteasyClient restClient = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = restClient.target(accessTokenURL);
		
		Form f = new Form();
		f.param("client_id", App_Id);
		f.param("client_secret", Secret_Id);
		f.param("redirect_uri",Redirect_URI );
		f.param("code", authCode);
		f.param("grant_type","authorization_code");
		
		Response response = target.request().accept(MediaType.APPLICATION_JSON).post(Entity.form(f) );
		
		String accessToken = response.readEntity(String.class);
		
		ObjectMapper mapper = new ObjectMapper();
		String token = null;
		try {
			token = mapper.readTree(accessToken).get("access_token").asText(); //readValue(fbToken, String.class);
		}  catch (IOException e) {
			e.printStackTrace();
		}
		restClient.close();
		return token;
	}

	public JsonNode getUserProfile(String accessToken)
	{
		String gmail_user_url= "https://www.googleapis.com/plus/v1/people/me";
		
	    System.out.println("gmail details"+gmail_user_url);
	    
		ResteasyClient restCall = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = restCall.target(gmail_user_url);
		String headerAuth="Bearer "+accessToken;
		Response response = target.request().header("Authorization", headerAuth).accept(MediaType.APPLICATION_JSON).get();
		
		String profile = response.readEntity(String.class);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode profiledata = null;
		try {
			profiledata=mapper.readTree(profile);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		restCall.close();
		System.out.println("profile details"+profile);
		return profiledata;
	}

}
