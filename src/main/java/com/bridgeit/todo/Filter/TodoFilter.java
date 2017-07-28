package com.bridgeit.todo.Filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bridgeit.todo.Utility.TokenManipulater;

public class TodoFilter implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(req.getServletContext());
		TokenManipulater tokenManipulater = context.getBean(TokenManipulater.class);

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		String accesstoken = request.getHeader("AccessToken");

		boolean accessTokenValidationResult = tokenManipulater.validateAccessToken(accesstoken);

		if (accessTokenValidationResult == false)
		{
			System.out.println("Your Access Token Expired.");
			response.setContentType("application/json");
			String jsonResp = "{\"status\":-4,\"errorMessage\":\"Access token is expired. Generate new Access Tokens\"}";
			response.getWriter().write(jsonResp);
			return ;
		}
		else
		{
			chain.doFilter(req, res);
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
	}

}
