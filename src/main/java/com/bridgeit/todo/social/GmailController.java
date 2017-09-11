package com.bridgeit.todo.social;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.todo.JSONResponse.UserResponse;
import com.bridgeit.todo.Utility.TokenManipulater;
import com.bridgeit.todo.model.Token;
import com.bridgeit.todo.model.User;
import com.bridgeit.todo.service.TokenService;
import com.bridgeit.todo.service.UserServices;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
public class GmailController {
	
	@Autowired
	private GmailLogin gmailLog;
	@Autowired
	UserServices userService;
	@Autowired
	TokenService tokservice;
	@Autowired
	UserResponse myresponse;
	@Autowired
	TokenManipulater manipulater;
	
	
	@RequestMapping(value="gmailconnection")
	public void googleConnection(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		String unid = UUID.randomUUID().toString();
		request.getSession().setAttribute("STATE", unid);
		String googleLoginURL = gmailLog.getGoogleAuthURL(unid);
		try {
			response.sendRedirect(googleLoginURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="gmail_login")
	public void redirectFromGoogle(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		String sessionState = (String) request.getSession().getAttribute("STATE");
		String googlestate = request.getParameter("state");
		
		if( sessionState == null || !sessionState.equals(googlestate) )
		{System.out.println("checking session body");
			response.sendRedirect("gmailconnection");
			return;
		}
		
		String error = request.getParameter("error");
		if( error != null && error.trim().isEmpty() ) 
		{System.out.println("checking error ,,,");
			response.sendRedirect("login");
			return;
		}
		String authCode = request.getParameter("code");
		String accessToken = gmailLog.getAccessToken(authCode);
		
		JsonNode profiledata= gmailLog.getUserProfile(accessToken);
		System.out.println("checking emails value,,"+profiledata.get("emails").get(0).get("value").asText());
		User userExist = userService.getUserById(profiledata.get("emails").get(0).get("value").asText());
		
		if(userExist == null)
		{
			System.out.println("Creating User");
			
			User addUser = new User();
			addUser.setName(profiledata.get("name").get("givenName").asText());
			addUser.setEmail(profiledata.get("emails").get(0).get("value").asText());
			addUser.setProfileImage(profiledata.get("image").get("url").asText());
			userService.saveUserDetails(addUser);
			
			Token token = manipulater.generateToken();
			token.setUser(addUser);
			
			request.getSession().setAttribute("token",token);
			request.getSession().setAttribute("UserInSession",addUser);
			
			try {
				tokservice.saveTokenDetail(token);
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
			response.sendRedirect("http://localhost:8011/ToDo/#!/redirect?tokeninurl=token");
		}
		
		Token token = manipulater.generateToken();
		token.setUser(userExist);
		
		request.getSession().setAttribute("token",token);
		request.getSession().setAttribute("UserInSession",userExist);
		try {
			tokservice.saveTokenDetail(token);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		response.sendRedirect("http://localhost:8011/ToDo/#!/redirect?tokeninurl=token");
	}

}
