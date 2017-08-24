package com.bridgeit.todo.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlValidate {

	public static final String URL_REGEX = "(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*"+
			"[-a-zA-Z0-9+&@#/%=~_|]";
	
	public static String isValidateUrl(String data) throws Exception{
		
		Pattern pattern = Pattern.compile(URL_REGEX);
		Matcher matcher = pattern.matcher(data);
		
		if(matcher.find()){
			int start = matcher.start();
			int end = matcher.end();
			return data.substring(start,end);
		} 
		return null;
		
	}
}
