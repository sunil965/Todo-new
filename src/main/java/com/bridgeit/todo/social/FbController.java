package com.bridgeit.todo.social;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.todo.JSONResponse.Response;
import com.bridgeit.todo.JSONResponse.UserResponse;
import com.bridgeit.todo.Utility.TokenManipulater;
import com.bridgeit.todo.model.Token;
import com.bridgeit.todo.model.User;
import com.bridgeit.todo.service.TokenService;
import com.bridgeit.todo.service.UserServices;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
public class FbController {
	
	@Autowired
	FbConnection fbconnection;
	@Autowired
	UserServices userService;
	@Autowired
	TokenManipulater manipulater;
	@Autowired
	TokenService tokservice;
	@Autowired
	UserResponse myresponse;

	@RequestMapping(value="/facebooklogin")
	public void loginwithfb(HttpServletRequest request, HttpServletResponse response)
	{
		System.out.println("in facebook controller");
		String fbloginurl= fbconnection.getFBAuthUrl();
		
		System.out.println("fburl :"+fbloginurl);
		
		try {
			response.sendRedirect(fbloginurl);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="todohome")
	public void redirecttoTodo(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		System.out.println("Redirected by facebook...");
		
		String dataa = request.getParameter("code");
		
		String fbtoken = fbconnection.getAccessToken(dataa);
		
		JsonNode fbUserData = fbconnection.getProfile(fbtoken);
		
		System.out.println("User Profile is fbUserData :" + fbUserData);
		
		User userExist = userService.getUserById(fbUserData.get("email").asText());
		
		if(userExist == null)
		{
			User addUser = new User();
			addUser.setName(fbUserData.get("first_name").asText());
			addUser.setEmail(fbUserData.get("email").asText());
			addUser.setProfileImage(fbUserData.get("picture").get("data").get("url").asText());
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
	
	
	
	@RequestMapping(value = "/getTokenByUrl", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Response> getTokenByUrl(HttpServletRequest request, HttpServletResponse response) {
	
		String tokenKey = request.getHeader("tt");
		Token token = (Token) request.getSession().getAttribute(tokenKey);
		System.out.println("Froom Session Token is:: "+token);
		myresponse.setStatus(5);
		myresponse.setMessage("Logged in Successfully");
		myresponse.setToken(token);
		return new ResponseEntity<Response>(myresponse, HttpStatus.OK);
	}
}
