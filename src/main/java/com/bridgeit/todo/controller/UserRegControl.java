package com.bridgeit.todo.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.todo.JSONResponse.ErrorResponse;
import com.bridgeit.todo.JSONResponse.Response;
import com.bridgeit.todo.JSONResponse.UserResponse;
import com.bridgeit.todo.Utility.Encryptor;
import com.bridgeit.todo.Utility.SendEmail;
import com.bridgeit.todo.model.User;
import com.bridgeit.todo.service.UserServices;
import com.bridgeit.todo.validation.UserRegValidate;

@RestController
public class UserRegControl {

	@Autowired
	UserRegValidate validator;
	@Autowired
	UserServices service;
	@Autowired
	UserResponse myresponse;
	@Autowired
	SendEmail sendEmail;

	private final static Logger logger = Logger.getLogger("sunil");
	private final static Logger logger1 = Logger.getRootLogger();

	UserResponse userResponse = new UserResponse();
	ErrorResponse errorResponse = new ErrorResponse();

	/**
	 * This controller method persist user object to User_Registration_Table in`TodoApp` database. 
	 * @param user {@link User}
	 * @param result {@link BindingResult}
	 * @return {@link ResponseEntity<Response>}
	 * @throws Exception
	 */
	@RequestMapping(value = "/registration", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Response> registerUser(@RequestBody User user, BindingResult result, HttpServletRequest request) throws Exception {

		validator.validate(user, result);
		System.out.println(result.hasErrors());
		System.out.println("Modal class Image is::::"+user);
		
		
		if (result.hasErrors()) {
			logger1.debug("Registration Failed!");
			logger.debug("Registration Failed!");

			List<FieldError> list = result.getFieldErrors();
			errorResponse.setList(list);
			errorResponse.setStatus(-1);
			errorResponse.setMessage("Result binding error ....");

			return new ResponseEntity<Response>(errorResponse, HttpStatus.NO_CONTENT);
		}

		String pass = user.getPassword();
		String encPass = Encryptor.getDigest(pass);
		user.setPassword(encPass);
		
		request.getSession().setAttribute("emailkey", user.getEmail());
		sendEmail.sendVerificationMail(user.getEmail(), "emailkey");
		
		try {
			service.saveUserDetails(user);
			logger1.debug("Registration Succesfull!");
			logger.debug("Registration Succesfull!");
			userResponse.setStatus(1);
			userResponse.setMessage("User Successfully Added....");
//			userResponse.setUser(user);
			return new ResponseEntity<Response>(userResponse, HttpStatus.OK);

		} catch (Exception e) {
			logger1.debug("Registration Failed!");
			logger.debug("Registration Failed!");
			e.printStackTrace();
			errorResponse.setStatus(-1);
			errorResponse.setMessage("some internal DAtabase server error...");
			return new ResponseEntity<Response>(errorResponse, HttpStatus.NOT_ACCEPTABLE);
		}
	}

	/**
	 * This controller method updates user object to User_Registration_Table in`TodoApp` database.
	 * @param user user {@link User}
	 * @param result {@link BindingResult}
	 * @return {@link ResponseEntity<Response>}
	 * @throws Exception
	 */
	@RequestMapping(value = "/rest/update", method = RequestMethod.PUT, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Response> updateUser(@RequestBody User user, BindingResult result) throws Exception {

		validator.validate(user, result);
		if (result.hasErrors()) {
			logger.debug("Update is not sucessful!");
			List<FieldError> list = result.getFieldErrors();
			errorResponse.setList(list);
			errorResponse.setStatus(-1);
			errorResponse.setMessage("Result binding error ....");
			return new ResponseEntity<Response>(errorResponse, HttpStatus.NOT_ACCEPTABLE);
		}

		String pass = user.getPassword();
		String encPass = Encryptor.getDigest(pass);
		user.setPassword(encPass);

		try {
			service.updateUserDetails(user);
			logger.debug("Update sucessful!");
			userResponse.setStatus(1);
			userResponse.setMessage("User Successfully Added....");
//			userResponse.setUser(user);
			return new ResponseEntity<Response>(userResponse, HttpStatus.OK);
		} 
		catch (Exception e) {
			logger1.debug("Registration Failed!");
			logger.debug("Registration Failed!");
			e.printStackTrace();
			errorResponse.setStatus(-1);
			errorResponse.setMessage("some internal DAtabase server error...");
			return new ResponseEntity<Response>(errorResponse, HttpStatus.NOT_ACCEPTABLE);
		}
	}

	/**
	 * This method get details of a particular user with the given id in url.
	 * @param id {@link Integer}
	 * @return {@link ResponseEntity<List>}}
	 */
	@RequestMapping(value = "/getUserDetailsById", method=RequestMethod.POST)//@RequestMapping(value = "/getUserDetailsById/{email:.+}")
	public ResponseEntity<Response> getUserDetailsById(@RequestBody Object obj) {
		
		String email = obj.toString();
		String a=email.substring(email.indexOf("=")+1,email.length()-1);
		
		User u = service.getUserById(a);
		if(u != null){
			System.out.println("User u is .. "+u);
			myresponse.setStatus(1);
			myresponse.setMessage("User Found");
			myresponse.setUser(u);
			return new ResponseEntity<Response>(myresponse, HttpStatus.OK);
		}
		System.out.println("User not found .. ");
		myresponse.setStatus(-1);
		myresponse.setMessage("User Not Found");
		return new ResponseEntity<Response>(myresponse, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/rest/getLoginUser", method=RequestMethod.GET)
	public ResponseEntity<Response> getLoginUser(HttpServletRequest request) {
		User userInSession = (User) request.getSession().getAttribute("UserInSession");
		String emailofUserInSession = userInSession.getEmail();
		User loginuser = service.getUserById(emailofUserInSession);
		if(loginuser != null){
			myresponse.setStatus(1);
			myresponse.setMessage("User Found");
			myresponse.setToken(null);
			myresponse.setUser(loginuser);
			return new ResponseEntity<Response>(myresponse, HttpStatus.OK);
		}
		else{
			myresponse.setStatus(-1);
			myresponse.setMessage("User Not Found");
			return new ResponseEntity<Response>(myresponse, HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="/emailVerification")
	public void verifyEmail(@RequestParam String email, HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("In emailVerification where email is :"+email);
		String emialToVerify = (String) request.getSession().getAttribute(email);
		service.activateUser(emialToVerify);
		try {
			response.sendRedirect("http://localhost:8080/ToDo/#!/login");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping(value="/sendResetPasswordUrl")
	public ResponseEntity<Response> resetPasswordUrl(@RequestBody Map<String, String> emailMap, HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("In sendResetPasswordUrl where email is :"+emailMap.get("getemail"));
		request.getSession().setAttribute("resetpasswordemail", emailMap.get("getemail"));
		sendEmail.resetPassword(emailMap.get("getemail"), "resetpasswordemail");
		
		myresponse.setStatus(101);
		myresponse.setMessage("Every thing is ok..");
		return new ResponseEntity<Response>(myresponse, HttpStatus.OK);
	}
	
	@RequestMapping(value="/resetPasswordApi")
	public void resetPassword(@RequestParam String email, HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("In resetPasswordApi where email is :"+email);
		try {
			response.sendRedirect("http://localhost:8080/ToDo/#!/newpasswordpage");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/updatepassword")
	public ResponseEntity<Response> updatePassword(@RequestBody Map<String, String> passwordMap, HttpServletRequest request, HttpServletResponse response){
		String newpassword = passwordMap.get("password");
		System.out.println("In updatepassword API where password is :"+newpassword);
		
		
		
		String updateonemail = (String) request.getSession().getAttribute("resetpasswordemail");
		System.out.println("In updatepassword API where updateonemail is :"+updateonemail);
		try {
			String encPass = Encryptor.getDigest(newpassword);
			service.saveUpdatePassword(updateonemail, encPass);
			myresponse.setStatus(102);
			myresponse.setMessage("Every thing is ok..");
			return new ResponseEntity<Response>(myresponse, HttpStatus.OK);
		} 
		catch (Exception e) {
			e.printStackTrace();
			myresponse.setStatus(100);
			myresponse.setMessage("Password Updation Failed..");
			return new ResponseEntity<Response>(myresponse, HttpStatus.OK);
		}
		
	}
	@RequestMapping(value="rest/setProfilePic")
	public ResponseEntity<Response> setProfilePic(@RequestBody User user){
		
		String emailid = user.getEmail();
		String dpimage = user.getProfileImage();
		try {
			service.changeDp(emailid, dpimage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
}
