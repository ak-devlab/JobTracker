package com.example.jobtracker.web;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/google-login")
public class GoogleLoginServlet extends HttpServlet {
	
	private static final String REDIRECT_URL = "https://jobtracker-f80h.onrender.com/oauth2callback";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	
	throws ServletException, IOException{
		String clientId = System.getenv("GOOGLE_CLIENT_ID");
		
		byte[] randomBytes = new byte[32];
		new SecureRandom().nextBytes(randomBytes);
		String state = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
				
		HttpSession session = req.getSession();
				session.setAttribute("oauth_state", state);
				
				String url = "https://accounts.google.com/o/oauth2/v2/auth"
						+ "?client_id=" + enc(clientId)
						+ "&redirect_uri=" + enc(REDIRECT_URL)
						+ "&response_type=code"
						+ "&scope=" + enc("openid email profile")
						+ "&state=" + enc(state)
						+ "&prompt=select_account";
				
				resp.sendRedirect(url);
	}
	  private String enc(String value) {
		  return URLEncoder.encode(value, StandardCharsets.UTF_8);
				
	}

}
