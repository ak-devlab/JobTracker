package com.example.jobtracker.web;

import java.io.IOException;

import java.util.Arrays;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.example.jobtracker.model.User;
import com.example.jobtracker.repo.UserRepo;


@WebServlet("/oauth2callback")
public class OAuthCallbackServlet extends HttpServlet {
	private static final String REDIRECT_URL =
			"https://jobtracker-f80h.onrender.com/oauth2callback";
	
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
    	
    	String code = req.getParameter("code");
    	String state = req.getParameter("state");
    	
    	HttpSession session = req.getSession();
    	String savedState = (String) session.getAttribute("oauth_state");
    	
    	if(savedState == null || !savedState.equals(state)) {
    		resp.getWriter().println("state check failed");
    		return;
    	}
    	
    	String clientId = System.getenv("GOOGLE_CLIENT_ID");
    	String clientSecret = System.getenv("GOOGLE_CLIENT_SECRET");
    	
    	var tokenResponse = new GoogleAuthorizationCodeTokenRequest(
    			new NetHttpTransport(),
    			GsonFactory.getDefaultInstance(),
    			"https://oauth2.googleapis.com/token",
    			clientId,
    			clientSecret,
    			code,
    			REDIRECT_URL
    			).execute();
    	
    	GoogleIdToken idToken = tokenResponse.parseIdToken();
    	GoogleIdToken.Payload payload = idToken.getPayload();
    	
    	String googleSub = payload.getSubject();
    	String email = payload.getEmail();
    	String name = (String) payload.get("name");
    	String picture = (String) payload.get("picture");
    	
    	session.setAttribute("google_sub", googleSub);
    	session.setAttribute("email", email);
    	session.setAttribute("name", name);
    	session.setAttribute("picture", picture);
    	
    	resp.setContentType("text/plain; charset=UTF-8");
    	UserRepo repo = new UserRepo();
    	User user = repo.findOrCreate(googleSub, email, name, picture);
    	
    	if(user == null) {
    		resp.getWriter().println("user save failed");
    		return;
    	}
    	
    	HttpSession session = req.getSession();
    	
    	session.setAttribute("user_id", user.id);
    	session.setAttribute("user_name", user.name);
    	
    	resp.sendRedirect(req.getContextPath()+"/app?group=all");
    	
    	
    	
    }
}
