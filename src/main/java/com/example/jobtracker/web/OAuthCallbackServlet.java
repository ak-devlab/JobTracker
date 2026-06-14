package com.example.jobtracker.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/oauth2callback")
public class OAuthCallbackServlet extends HttpServlet {
	
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
    	
    	resp.getWriter().println("callback OK. code =" + code);
    	
    }
}
