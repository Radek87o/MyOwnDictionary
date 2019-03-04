package com.radek.myownplengdictionary.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.radek.myownplengdictionary.entity.User;
import com.radek.myownplengdictionary.service.UserService;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	
	@Autowired
	private UserService userService;
	
	
	//passing user attributes to home page, when user is successfully logged in
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		String username = authentication.getName();
		User theUser = userService.findByUsername(username);
		
		HttpSession session = request.getSession();
		session.setAttribute("user", theUser);
		response.sendRedirect(request.getContextPath()+"/dictionary/addWord");
	}

}
