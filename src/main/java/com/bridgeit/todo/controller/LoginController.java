package com.bridgeit.todo.controller;

//import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.todo.JSONResponse.Response;
import com.bridgeit.todo.JSONResponse.UserResponse;
import com.bridgeit.todo.Utility.Encryptor;
import com.bridgeit.todo.Utility.TokenManipulater;
import com.bridgeit.todo.Utility.Redis.TokenRepository;
import com.bridgeit.todo.model.Token;
import com.bridgeit.todo.model.User;
import com.bridgeit.todo.service.RedisService;
import com.bridgeit.todo.service.TokenService;
import com.bridgeit.todo.service.UserServices;

import redis.clients.jedis.Jedis;

/**
 * Login Controller
 * 
 * @author sunil kumar
 *
 */
@RestController
public class LoginController {

	@Autowired
	UserServices service;
	@Autowired
	TokenService tokservice;
	@Autowired
	UserResponse myresponse;
	@Autowired
	TokenManipulater manipulater;
	
	@Autowired
	RedisService redisService;
	
	
	private final static Logger logger = Logger.getLogger("loginLog");

	/**
	 * this controller method login the user
	 * @param user {@link User}
	 * @param request {@link HttpServletRequest}
	 * @return {@link ResponseEntity<Response>}
	 * @throws Exception
	 */
	@RequestMapping(value = "/todoLogin", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Response> todoLogin(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Token token = manipulater.generateToken();
		String encPass = Encryptor.getDigest(user.getPassword());
		user.setPassword(encPass);

/*		Jedis jedis = new Jedis("localhost");
		System.out.println(jedis.ping());*/
		
		User userresult = null;
		try	{
			userresult = service.loginWithTodo(user.getEmail(), user.getPassword());
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

		if (userresult != null)	{
			System.out.println("Logged in "+userresult);
			logger.debug("Logged in sucessfully!");
			HttpSession httpsession = request.getSession();
			httpsession.setAttribute("UserInSession", userresult);
			
			token.setUser(userresult);
			
			try {
				tokservice.saveTokenDetail(token); // Saving Token object in Database.
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
			redisService.saveToken(token); // Saving Token object in Redis.
			token.setUser(null);
			myresponse.setStatus(1);
			myresponse.setMessage("Logged in Successfully");
			myresponse.setToken(token);
			return new ResponseEntity<Response>(myresponse, HttpStatus.OK);
		} 
		else {
			myresponse.setStatus(-1);
			myresponse.setMessage("Logged in Unsuccessfull");
			return new ResponseEntity<Response>(myresponse, HttpStatus.OK);
		}
	}
	
	
	/**
	 * this controller method generates new access token if refresh token is valid and return new token in case where access token is expired 
	 * but when refresh token is also expired then return null as token.
	 * 
	 * @param request {@link HttpServletRequest}
	 * @return {@link ResponseEntity<Response>}
	 */
	@RequestMapping(value="/generateNewAccessToken", method=RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Response> generateNewAccessToken(HttpServletRequest request)
	{
		String oldaccess = request.getHeader("AccessToken");
		
		Token token = manipulater.validateRefreshToken(oldaccess);
		if(token!=null){
			token.setUser(null);
			myresponse.setStatus(1);
			myresponse.setMessage("Token Update is Successful");
			myresponse.setToken(token);
			return new ResponseEntity<Response>(myresponse,HttpStatus.OK);
		}
		myresponse.setStatus(-1);
		myresponse.setMessage("Token Updation Unsuccessful");
		return new ResponseEntity<Response>(myresponse,HttpStatus.OK);
	}
	
	

	/**
	 * this method will delete token object from Database on logout.
	 * 
	 * @param httpServletRequest
	 * @return {@link ResponseEntity<Void>}
	 */
	@RequestMapping(value = "/logout")
	public ResponseEntity<Void> logout(HttpServletRequest httpServletRequest) {
		
		String deletetoken = httpServletRequest.getHeader("AccessToken");
		System.out.println("delete by this token "+deletetoken);
		try {
			tokservice.deleteToken(deletetoken);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
