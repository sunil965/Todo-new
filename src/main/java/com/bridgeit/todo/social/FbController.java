package com.bridgeit.todo.social;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.todo.JSONResponse.Response;

@RestController
public class FbController {
	
	@Autowired
	FbConnection fbconnection;

	@RequestMapping(value="/facebooklogin")
	public void loginwithfb(HttpServletRequest request, HttpServletResponse response)
	{
		System.out.println("in facebook controller");
		String fbloginurl= fbconnection.getFBAuthUrl();
		
		System.out.println("fburl :"+fbloginurl);
		
		try 
		{
			response.sendRedirect(fbloginurl);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="todohome")
	public ResponseEntity<Response> redirecttoTodo(HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("Redirected by facebook...");
		
		String data = request.getParameter("code");
		
		String fbtoken = fbconnection.getAccessToken(data);
		
		System.out.println("facebook tok :" + fbtoken);
		return null;
	}
}
