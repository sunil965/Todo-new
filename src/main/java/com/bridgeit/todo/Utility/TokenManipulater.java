package com.bridgeit.todo.Utility;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bridgeit.todo.model.Token;
import com.bridgeit.todo.service.TokenService;

@Component
public class TokenManipulater {

	/*static Token token = new Token();*/

	@Autowired
	TokenService tokenService;

	/************** Access Token Generator Method *************/

	public Token generateToken() {
		Token token = new Token();
		String accesstoken = UUID.randomUUID().toString().replaceAll("-", "");
		token.setAccesstoken(accesstoken);

		String refreshtoken = UUID.randomUUID().toString().replaceAll("-", "");
		token.setRefreshtoken(refreshtoken);

		token.setCreatedOn(new Date());
		return token;
	}

	/************** New Access Token Generator Method *************/

	public Token generateNewAccessToken(Token token) {
		String accesstoken = UUID.randomUUID().toString().replaceAll("-", "");
		token.setAccesstoken(accesstoken);
		/*token.setCreatedOn(new Date());*/
		return token;
	}

	/************** Check Access Token Validity Method *************/

	public boolean validateAccessToken(String accesstoken) {
			
		Token retuturnedtoken = (Token) tokenService.getTokenfromDB(accesstoken);
		if(retuturnedtoken!=null)
		{
			long generatedTime = retuturnedtoken.getCreatedOn().getTime();
			long currentTime = new Date().getTime();
			long difference = currentTime - generatedTime;
			long differrenceInMinute = TimeUnit.MILLISECONDS.toMinutes(difference);
			if (differrenceInMinute > 45) {
				return false;
			}	
			else
				return true;
		}
		return false;
		}
	
	
	/************** Check Refresh Token Validity Method *************/

	public Token validateRefreshToken(String accesstoken) {
		
		Token retuturned = (Token) tokenService.getTokenfromDB(accesstoken);

		long generatedTime = retuturned.getCreatedOn().getTime();
		long currentTime = new Date().getTime();
		long difference = currentTime - generatedTime;
		long differrenceInMinute = TimeUnit.MILLISECONDS.toMinutes(difference);
		
		if (differrenceInMinute < 60) {
			Token newToken = generateNewAccessToken(retuturned);
			tokenService.updateAccessToken(newToken);
			return newToken;
		}
		tokenService.deleteToken(accesstoken);
		return null;
	}

}
