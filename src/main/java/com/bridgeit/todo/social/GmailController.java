package com.bridgeit.todo.social;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.todo.model.Token;
import com.bridgeit.todo.model.User;
import com.bridgeit.todo.service.TokenService;
import com.bridgeit.todo.service.UserServices;

@RestController
public class GmailController {
	
	@Autowired
	private GmailLogin gmailLog;
	
	@Autowired
	UserServices userService;
	
	@Autowired
	TokenService tokenService;
	
	Token token = new Token();
	
	@RequestMapping(value="gmailconnection")
	public void googleConnection(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		String unid = UUID.randomUUID().toString();
		request.getSession().setAttribute("STATE", unid);
		String googleLoginURL = gmailLog.getGoogleAuthURL(unid);
		response.sendRedirect(googleLoginURL);
		return;
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
	/*	if( error != null && error.trim().isEmpty() ) 
		{System.out.println("checking error ,,,");
			response.sendRedirect("login");
			return;
		}*/
		String authCode = request.getParameter("code");
		String accessToken = gmailLog.getAccessToken(authCode);
//		System.out.println("Token by google "+accessToken);
		token.setAccesstoken(accessToken);
//		token.setUser(user);
		tokenService.saveTokenDetail(token);
		
		
		/*User profile= gmailLog.getUserProfile(accessToken);
		User user = userService.getUserById(profile.getEmail());
		
		token.setAccesstoken(accessToken);
		token.setUser(user);
		tokenService.saveTokenDetail(token);*/

		response.setHeader("AccessToken", token.getAccesstoken());
		
		response.sendRedirect("http://localhost:8011/ToDo/#!/notes");
	}

	
}
